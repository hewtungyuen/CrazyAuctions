/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AuctionListingEntity;
import java.util.Collection;
import java.util.Date;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.NoSuchObjectLocalException;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.enumeration.AuctionListingStateEnum;

/**
 *
 * @author hewtu
 */
@Stateless
public class AuctionListingTimerSessionBean implements AuctionListingTimerSessionBeanRemote, AuctionListingTimerSessionBeanLocal {

    @EJB
    private BidEntitySessionBeanLocal bidEntitySessionBeanLocal;

    @PersistenceContext(unitName = "CrazyAuctions-ejbPU")
    private EntityManager em;

    @Resource
    private SessionContext sessionContext;

    @Override
    public void createAuctionTimers(Long auctionListingId) {
        AuctionListingEntity a = em.find(AuctionListingEntity.class, auctionListingId);
        Date startDate = a.getStartDate();
        Date endDate = a.getEndDate();

        TimerService timerService = sessionContext.getTimerService();

        timerService.createTimer(startDate, auctionListingId);
        timerService.createTimer(endDate, auctionListingId);
        System.out.println("Created timers");
    }

    @Timeout
    public void handleTimeout(Timer t) {
        System.out.println("TIMEOUT");
        Long auctionListingId = (Long) t.getInfo();
        AuctionListingEntity a = em.find(AuctionListingEntity.class, auctionListingId);

        if (a.getAuctionListingState().equals(AuctionListingStateEnum.CLOSED)) {
            System.out.println("OPENING");
            a.setAuctionListingState(AuctionListingStateEnum.OPEN);
        } else if (a.getAuctionListingState().equals(AuctionListingStateEnum.OPEN)) {
            System.out.println("CLOSING");
            a.setAuctionListingState(AuctionListingStateEnum.CLOSED);
            bidEntitySessionBeanLocal.markWinningBid(auctionListingId);
        }
    }

    @Override
    public void cancelTimers(Long auctionListingId) {
        TimerService timerService = sessionContext.getTimerService();
        Collection<Timer> timers = timerService.getTimers();

        for (Timer t : timers) {
            if (t.getInfo() != null) {
                if (t.getInfo().equals(auctionListingId)) {
                    try {
                        t.cancel();
                        System.out.println("Cancelled timer");
                    } catch (NoSuchObjectLocalException ex) {
                        System.err.println("Timer already cancelled");
                    }
                }
            }
        }
    }

}
