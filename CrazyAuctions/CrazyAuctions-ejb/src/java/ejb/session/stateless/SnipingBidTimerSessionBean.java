/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import ejb.session.singleton.BidIncrementSessionBeanLocal;
import entity.AuctionListingEntity;
import entity.BidEntity;
import entity.CustomerEntity;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.InsufficientBalanceException;
import util.exception.NoAuctionListingBidsException;
import util.helperclass.SnipingBidDetails;

/**
 *
 * @author hewtu
 */
@Stateless
public class SnipingBidTimerSessionBean implements SnipingBidTimerSessionBeanRemote, SnipingBidTimerSessionBeanLocal {

    @EJB
    private BidEntitySessionBeanLocal bidEntitySessionBeanLocal;

    @EJB
    private CustomerEntitySessionBeanLocal customerEntitySessionBeanLocal;

    @EJB
    private BidIncrementSessionBeanLocal bidIncrementSessionBeanLocal;

    @PersistenceContext(unitName = "CrazyAuctions-ejbPU")
    private EntityManager em;

    @Resource
    private SessionContext sessionContext;

    @Timeout
    public void timeout(Timer t) {
        SnipingBidDetails snipingBidDetails = (SnipingBidDetails) t.getInfo();

        CustomerEntity c = em.find(CustomerEntity.class, snipingBidDetails.getCustomerId());
        AuctionListingEntity a = em.find(AuctionListingEntity.class, snipingBidDetails.getAuctionListingId());
        BigDecimal snipingBidPrice = snipingBidDetails.getSnipingBidPrice();

        BigDecimal incrementedPrice = bidIncrementSessionBeanLocal.incrementPrice(a.getCurrentBidPrice());

        if (c.getIsLoggedIn() == false) {
            System.out.println("Premium customer + " + c.getUsername() + " is not logged in so sniping bid failed");
            return;
        }

        if (snipingBidPrice.compareTo(incrementedPrice) < 0) {
            System.out.println("Premium customer + " + c.getUsername() + " sniping bid price is too low");
            return;
        }
        try {
            bidEntitySessionBeanLocal.createNewBid(c.getId(), a.getId(), snipingBidPrice);
            System.out.println("Premium customer + " + c.getUsername() + " successfully placed sniping bid");
        } catch (InsufficientBalanceException ex) {
            System.out.println("Premium customer + " + c.getUsername() + " does not have enough credit balance for sniping bid");
        }
    }

    @Override
    public void createTimer(SnipingBidDetails snipingBidDetails) throws InsufficientBalanceException {
        CustomerEntity c = em.find(CustomerEntity.class, snipingBidDetails.getCustomerId());
        AuctionListingEntity a = em.find(AuctionListingEntity.class, snipingBidDetails.getAuctionListingId());
        BigDecimal snipingBidPrice = snipingBidDetails.getSnipingBidPrice();

        BigDecimal incrementedPrice = bidIncrementSessionBeanLocal.incrementPrice(a.getCurrentBidPrice());

        if (snipingBidPrice.compareTo(incrementedPrice) < 0) {
            throw new InsufficientBalanceException("Premium customer " + c.getUsername() + "'s sniping bid price is too low");
        }
        TimerService timerService = sessionContext.getTimerService();
        timerService.createTimer(snipingBidDetails.getTriggerDate(), snipingBidDetails);

        System.out.println("Created sniping bid timer at " + snipingBidDetails.getTriggerDate());
    }

    public void persist(Object object) {
        em.persist(object);
    }
}
