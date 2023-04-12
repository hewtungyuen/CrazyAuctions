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
import entity.AddressEntity;
import entity.AuctionListingEntity;
import entity.BidEntity;
import entity.CustomerEntity;
import java.math.BigDecimal;
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
import util.exception.NoAuctionListingBidsException;
import util.exception.NoSuchAuctionListingException;
import util.exception.ProxyBidNotLargeEnoughException;

/**
 *
 * @author hewtu
 */
@WebService(serviceName = "PremiumCustomerWebService")
@Stateless()
public class PremiumCustomerWebService {

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
    public CustomerEntity premiumRegistration(@WebParam(name = "username") String username, @WebParam(name = "password") String password) {
        CustomerEntity c = customerEntitySessionBeanLocal.createCustomer(username, password);
        c.setCustomerType(CustomerTypeEnum.PREMIUM);
        CustomerEntity premiumCustomer = customerEntitySessionBeanLocal.updateCustomer(c);
        return premiumCustomer;
    }

    @WebMethod(operationName = "remoteLogin")
    public CustomerEntity remoteLogin(@WebParam(name = "username") String username, @WebParam(name = "password") String password) throws AuthenticationException {
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
        em.detach(a);
        if (b.getAuctionListing() != null) {
            em.detach(b);
            b.setAuctionListing(null);
        }
        return a;
    }

    @WebMethod(operationName = "remoteBrowseAllAuctionListings")
    public List<AuctionListingEntity> remoteBrowseAllAuctionListings() {
        List<AuctionListingEntity> listings = auctionListingEntitySessionBeanLocal.viewAllOpenAuctionListings();

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
    public BidEntity configureSnipingBid(@WebParam(name = "snipingBid") BidEntity snipingBid) {
        // check if the user has enough credits 
        // debit from the customer 
        
        // create timer to trigger _ minutes as specified by the user 
        
        // in the timer, 
            // check if the user is still logged in 
            // check if the sniping bid price is minimally one increment higher than the current bid 
            // place a bid as per normal, with the specified price 
            // if cmi, refund the customer the original sniping bid amount 
            
        return null;
    }
}
