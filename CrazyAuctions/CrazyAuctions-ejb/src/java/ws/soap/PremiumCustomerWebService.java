/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.soap;

import ejb.session.singleton.BidIncrementSessionBeanLocal;
import ejb.session.stateless.AuctionListingEntitySessionBeanLocal;
import ejb.session.stateless.BidEntitySessionBeanLocal;
import ejb.session.stateless.CustomerEntitySessionBeanLocal;
import ejb.session.stateless.SnipingBidTimerSessionBeanLocal;
import entity.AuctionListingEntity;
import entity.BidEntity;
import entity.CustomerEntity;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.enumeration.CustomerTypeEnum;
import util.exception.AuthenticationException;
import util.exception.InsufficientBalanceException;
import util.exception.InvalidDateInputException;
import util.exception.NoAuctionListingBidsException;
import util.exception.ProxyBidNotLargeEnoughException;
import util.helperclass.SnipingBidDetails;

/**
 *
 * @author hewtu
 */
@WebService(serviceName = "PremiumCustomerWebService")
@Stateless()
public class PremiumCustomerWebService {

    @EJB
    private SnipingBidTimerSessionBeanLocal snipingBidTimerSessionBeanLocal;

    @EJB
    private BidIncrementSessionBeanLocal bidIncrementSessionBeanLocal;

    @EJB
    private BidEntitySessionBeanLocal bidEntitySessionBeanLocal;

    @EJB
    private CustomerEntitySessionBeanLocal customerEntitySessionBeanLocal;
    @PersistenceContext(unitName = "CrazyAuctions-ejbPU")
    private EntityManager em;

    @EJB
    private AuctionListingEntitySessionBeanLocal auctionListingEntitySessionBeanLocal;

    @WebMethod(operationName = "premiumRegistration")
    public CustomerEntity premiumRegistration(@WebParam(name = "username") String username, @WebParam(name = "password") String password) throws AuthenticationException {
        CustomerEntity c = customerEntitySessionBeanLocal.login(username, password);
        c.setCustomerType(CustomerTypeEnum.PREMIUM);
        c.setIsLoggedIn(Boolean.FALSE);
        CustomerEntity premiumCustomer = customerEntitySessionBeanLocal.updateCustomer(c);
        return premiumCustomer;

    }

    @WebMethod(operationName = "remoteLogin")
    public CustomerEntity remoteLogin(@WebParam(name = "username") String username, @WebParam(name = "password") String password) throws AuthenticationException {
        CustomerEntity c;

        try {
            c = customerEntitySessionBeanLocal.getCustomerByUsername(username);
        } catch (Exception ex) {
            throw new AuthenticationException("No such user");
        }
        if (c.getCustomerType().equals(CustomerTypeEnum.BASIC)) {
            throw new AuthenticationException(username + " is not a premium customer");
        }
        return customerEntitySessionBeanLocal.login(username, password);
    }

    @WebMethod(operationName = "remoteLogout")
    public void remoteLogout(@WebParam(name = "customerId") Long customerId) throws AuthenticationException {
        customerEntitySessionBeanLocal.logout(customerId);
    }

    @WebMethod(operationName = "remoteViewCreditBalance")
    public BigDecimal remoteViewCreditBalance(@WebParam(name = "customerId") Long customerId) {
        CustomerEntity c = customerEntitySessionBeanLocal.getCustomer(customerId);
        return c.getCreditBalance();
    }

    @WebMethod(operationName = "remoteViewAuctionListingDetails")
    public AuctionListingEntity remoteViewAuctionListingDetails(@WebParam(name = "productName") String productName) {
        AuctionListingEntity a = auctionListingEntitySessionBeanLocal.getAuctionListingByProductName(productName);
        BidEntity b = a.getWinningBid();
        if (b != null) {
            em.detach(a);
            em.detach(b);
            b.setAuctionListing(null);
        }
        return a;
    }

    @WebMethod(operationName = "remoteBrowseAllAuctionListings")
    public List<AuctionListingEntity> remoteBrowseAllAuctionListings() {
        List<AuctionListingEntity> listings = auctionListingEntitySessionBeanLocal.viewAllOpenAuctionListings();
        System.out.println("Size: " + listings.size());
        for (AuctionListingEntity a : listings) {
            BidEntity b = a.getWinningBid();
            if (b != null) {
                em.detach(a);
                em.detach(b);
                b.setAuctionListing(null);
            }
        }
        System.out.println("Size after detaching: " + listings.size());
        return listings;
    }

    @WebMethod(operationName = "remoteViewWonAuctionListings")
    public List<AuctionListingEntity> remoteViewWonAuctionListings(@WebParam(name = "customerId") Long customerId) {
        List<AuctionListingEntity> listings = auctionListingEntitySessionBeanLocal.browseWonAuctionListings(customerId);

        for (AuctionListingEntity a : listings) {
            BidEntity b = a.getWinningBid();
            em.detach(a);
            if (b.getAuctionListing() != null) {
                em.detach(b);
                b.setAuctionListing(null);
            }
        }
        return listings;
    }

    @WebMethod(operationName = "configureProxyBidding")
    public BidEntity configureProxyBidding(@WebParam(name = "proxyBid") BidEntity proxyBid) throws ProxyBidNotLargeEnoughException {

        AuctionListingEntity a = em.find(AuctionListingEntity.class, proxyBid.getAuctionListing().getId());

        BigDecimal currentBidPrice = a.getCurrentBidPrice();
        BigDecimal newBidPrice = bidIncrementSessionBeanLocal.incrementPrice(currentBidPrice);

        if (proxyBid.getBidPrice().compareTo(newBidPrice) < 0) {
            throw new ProxyBidNotLargeEnoughException("Proxy bid is not large enough. Enter a larger value.");
        }

        em.persist(proxyBid);
        customerEntitySessionBeanLocal.debit(proxyBid.getCustomer().getId(), proxyBid.getBidPrice(), "Placed proxy bid for " + a.getProductName());
        a.setCurrentBidPrice(newBidPrice);

        try {
            BidEntity previousBid = bidEntitySessionBeanLocal.getHighestBidForAuctionListing(proxyBid.getAuctionListing().getId());
            CustomerEntity previousCustomer = previousBid.getCustomer();
            customerEntitySessionBeanLocal.credit(previousCustomer.getId(), previousBid.getBidPrice(), "Outbidded for " + a.getProductName());
        } catch (NoAuctionListingBidsException ex) {

        }

        em.flush();
        return proxyBid;
    }

    @WebMethod(operationName = "configureSnipingBid")
    public void configureSnipingBid(@WebParam(name = "customerId") Long customerId, @WebParam(name = "auctionListingId") Long auctionListingId, @WebParam(name = "bidPrice") BigDecimal bidPrice, @WebParam(name = "minutes") Long minutes) throws InsufficientBalanceException, InvalidDateInputException {

        AuctionListingEntity a = em.find(AuctionListingEntity.class, auctionListingId);

        LocalDateTime auctionCloseDate = LocalDateTime.ofInstant(a.getEndDate().toInstant(), ZoneId.systemDefault());
        LocalDateTime triggerDate = auctionCloseDate.minusMinutes(minutes);
        Timestamp t = Timestamp.valueOf(triggerDate);
        Date triggerDateFinal = new Date(t.getTime());

        if (triggerDateFinal.before(a.getStartDate())) {
            throw new InvalidDateInputException("Trigger time is before start date");
        }

        if (triggerDateFinal.before(new Date())) {
            throw new InvalidDateInputException("Trigger time is in the past");
        }

        SnipingBidDetails snipingBidDetails = new SnipingBidDetails(customerId, auctionListingId, bidPrice, triggerDateFinal);
        snipingBidTimerSessionBeanLocal.createTimer(snipingBidDetails);
    }
}
