package View;

import java.util.List;

public class Client {

    private static final Client instance = new Client();
    private List<List<List<String>>> record;

    private int currentPage = 0;

    private Client() { }

    public static Client getInstance() {
        return instance;
    }

    public void setRecord(List<List<List<String>>> list) {
        this.record = list;
    }

    public void printRecord() {
        for (List<String> page : record.get(currentPage)) {
            for (String str: page) {
                System.out.println(str);
            }
            System.out.println();
        }
        System.out.println("---PAGE " + (currentPage + 1) + " OF " + record.size() + "---");
    }

    public void nextPage() {
        if (!(currentPage == record.size() - 1)) {
            currentPage++;
            printRecord();
        } else {
            System.out.println("No more pages.");

        }
    }

    public void prevPage() {
        if (!(currentPage == 0)) {
            currentPage--;
            printRecord();
        } else {
            System.out.println("No more pages.");

        }
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
