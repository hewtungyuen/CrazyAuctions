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
        Date now = new Date();
        if (startDate.after(now)) {
            if (endDate.after(startDate)) {
                timerService.createTimer(startDate, auctionListingId);
                System.out.println("Created starting timer at " + startDate.toString());
                
                timerService.createTimer(endDate, auctionListingId);
                System.out.println("Created ending timer at " + endDate.toString());
            }
        }
    }

    @Timeout
    public void handleTimeout(Timer t) {
        Long auctionListingId = (Long) t.getInfo();
        AuctionListingEntity a = em.find(AuctionListingEntity.class, auctionListingId);

        if (a.getAuctionListingState().equals(AuctionListingStateEnum.CLOSED)) {
            System.out.println("Start date reached for " + auctionListingId);
            a.setAuctionListingState(AuctionListingStateEnum.OPEN);
        } else if (a.getAuctionListingState().equals(AuctionListingStateEnum.OPEN)) {
            System.out.println("End date reached for " + auctionListingId);
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
                        System.out.println("Cancelled timer for " + auctionListingId);
                    } catch (NoSuchObjectLocalException ex) {
                        System.err.println("Timer already cancelled");
                    }
                }
            }
        }
    }

}
