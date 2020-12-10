package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class BlackListThread extends Thread {

    private int amountOfServers;
    private int numberOfStartingServer;
    private String ipaddress;
    private AtomicInteger ocurrenceCount;
    private CopyOnWriteArrayList<Integer> blackListOcurrence;
    private int blackListAlarmCount;
    private HostBlacklistsDataSourceFacade skds;
    private AtomicInteger checkedListsCount;

    public BlackListThread(int amountOfServers, int numberOfStartingServer, String ipaddress, AtomicInteger ocurrenceCount, CopyOnWriteArrayList<Integer> blackListOcurrence, int blackListAlarmCount, HostBlacklistsDataSourceFacade skds, AtomicInteger checkedListsCount) {
        this.amountOfServers = amountOfServers;
        this.numberOfStartingServer = numberOfStartingServer;
        this.ipaddress = ipaddress;
        this.ocurrenceCount = ocurrenceCount;
        this.blackListOcurrence = blackListOcurrence;
        this.blackListAlarmCount = blackListAlarmCount;
        this.skds = skds;
        this.checkedListsCount = checkedListsCount;
        //System.out.println(numberOfStartingServer+", "+(numberOfStartingServer+amountOfServers));
    }

    @Override
    public void run() {
        for (int i = numberOfStartingServer; i < (amountOfServers + numberOfStartingServer) && ocurrenceCount.get() < blackListAlarmCount; i++) {
            checkedListsCount.getAndIncrement();
            if (skds.isInBlackListServer(i, ipaddress)) {
                blackListOcurrence.add(i);
                ocurrenceCount.getAndIncrement();
            }
        }
    }

}
