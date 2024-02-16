package example.pageModels;


import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BaseLocatorSearch {

    protected List<Locator> elementList = new ArrayList<>();
    List<CompletableFuture<Locator>> listOfThreads;
    private static ExecutorService executor = null;

    public Locator findElementByDefaultSelectors(Page page, String element) {
        elementList.clear();
        elementList.add(page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(element)));
        elementList.add(page.locator(element).getByRole(AriaRole.BUTTON));
        elementList.add(page.getByPlaceholder(element));
        elementList.add(page.locator(element));
        listOfThreads = assignThreadsForWebElementSearch(elementList);
        return findFirstFinishedThreadAndGetWebElement(listOfThreads);
    }
    protected  List<CompletableFuture<Locator>> assignThreadsForWebElementSearch(List<Locator> elementList){
        executor = Executors.newFixedThreadPool(elementList.size());
        List<CompletableFuture<Locator>> listOfThreads = new ArrayList<>(); // List to hold all the completable futures
        for (Locator eachElement : elementList) {// iterate list
            listOfThreads.add(CompletableFuture.supplyAsync(() -> eachElement, executor)); // add it to the list of futures
        }
        return listOfThreads;
    }

    protected static Locator findFirstFinishedThreadAndGetWebElement(List<CompletableFuture<Locator>> listOfThreads) {
        Instant startTime = Instant.now();
        Instant midTime = Instant.now();
        boolean elementFound = false; // to break the loop
        Locator returnElement = null; // for the return

        while (true) { // loop for timeout
            midTime = Instant.now();
            if ((Duration.between(startTime, midTime).toMillis() / 1000.0)>10.0){break;}
            for (CompletableFuture<Locator> thread : listOfThreads) { // loop each future
                if (thread.isDone()) { // check if its done. if the element is there, it will find it before any of the other wait.until timeouts
                    if (!thread.join().isVisible()){continue;}
                    try {// try catch will capture invalid locator strategies
                        returnElement = thread.join();
                        elementFound = true; // change to break the loop
                        break; // break inner for
                    } catch (Exception ignored) {}
                }
            }
            if (elementFound) {
                Instant endTime = Instant.now();
                System.out.println("Find took " + Duration.between(startTime, endTime).toMillis() / 1000.0 + "s");
                break;
            } // break from timeout for
        }
        executor.shutdownNow();
        return returnElement;
    }
}