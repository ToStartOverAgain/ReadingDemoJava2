/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readingnews;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author daolinh private static HashSet<String> linkSet = new HashSet<>();
    private static HashMap<String, News> mapNews = new HashMap<>();
 */
public class ReadingNews {

    private static ArrayList<News> listNews = new ArrayList<>();
    private static HashSet<String> linkSet = new HashSet<>();
    private static HashMap<String, News> mapNews = new HashMap<>();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        long startTime = System.currentTimeMillis();
       testMap();
        getSportLinks();
        ArrayList<Thread> listThread = new ArrayList<>();
//        linkSet.add("https://thethao.vnexpress.net/tin-tuc/tennis/federer-tuoi-tac-voi-toi-chi-la-con-so-3705342.html");
//        linkSet.add("https://thethao.vnexpress.net/tin-tuc/giai-ngoai-hang-anh/cuu-hlv-barca-la-ung-vien-hang-dau-thay-conte-tai-chelsea-3705306.html");
//        linkSet.add("https://thethao.vnexpress.net/tin-tuc/cac-giai-khac/de-bruyne-da-phat-tinh-quai-man-city-di-tiep-o-cup-fa-3705233.html");
//        linkSet.add("https://thethao.vnexpress.net/tin-tuc/cac-giai-khac/chelsea-vui-dap-newcastle-vao-vong-nam-cup-fa-3705203.html");
        
        // Lấy nội dung theo link.
        for (String string : linkSet) {
          News news = getNewsFromUrl(string);
            mapNews.put(string, news);
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    mapNews.put(string, new ReadingNews().getNewsFromUrl2(string));
                }
            });
            System.out.println(t.getName() + " started.");
            t.start();
            listThread.add(t);
        }
        
        for (Thread thread : listThread) {
            thread.join();
        }
        
        System.out.println("-----" + mapNews.size());
        for (Map.Entry<String, News> entry : mapNews.entrySet()) {
            String key = entry.getKey();
            News value = entry.getValue();            
            System.out.println(value.getTitle());
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Finish in " + (endTime - startTime) + " miliseconds.");
    }
    
//    public static void main(String[] args) throws IOException {
//        long startTime = System.currentTimeMillis();
//        
//        getSportLinks();
//        for (String string : linkSet) {
//            News news = getNewsFromUrl(string);
//            mapNews.put(string, news);            
//        }
//        for (Map.Entry<String, News> entry : mapNews.entrySet()) {
//            String key = entry.getKey();
//            News value = entry.getValue();
//            System.out.println(value.getTitle());
//        }
//        System.out.println(mapNews.size());
//        long endTime = System.currentTimeMillis();
//        System.out.println("Finish in: " + (endTime - startTime) + " miliseconds.");
//    }

    public static void testMap() {
        News n = new News();
        n.setTitle("News 1");
        News n1 = new News();
        n1.setTitle("News 2");
        mapNews.put("http://vnexpress.net/1", n);
        mapNews.put("http://vnexpress.net/2", n1);
        for (Map.Entry<String, News> entry : mapNews.entrySet()) {
            String key = entry.getKey();
            News value = entry.getValue();
            System.out.println(key);
            System.out.println(value.getTitle());
        }
        System.out.println(new Gson().toJson(mapNews));
    }

    public static void generateMenu() throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("===========================CRAWLER============================");
            System.out.println("1. Get content from url.");
            System.out.println("2. List news.");
            System.out.println("4. Get sport link.");
            System.out.println("5. Print sport link.");
            System.out.println("3. Exist.");
            System.out.println("Please enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    // get content from url.
                    System.out.println("Please enter url (from vnexpress.net only): ");
                    String url = scanner.nextLine();
                    News n = getNewsFromUrl(url);
                    if (n != null) {
                        listNews.add(n);
                    }
                    break;
                case 2:
                    // list content.
                    printListNews();
                    break;
                case 4:
                    getSportLinks();
                    break;
                case 5:
                    printSportLink();
                    break;
                case 3:
                    System.out.println("See you later!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }

    }

    public static void getSportLinks() throws IOException {
        Document document = Jsoup.connect("https://thethao.vnexpress.net/").get();
        linkSet.add(document.select("section.featured article .thumb_big a").attr("href"));
        Elements els = document.select("section.featured .sub_featured .title_news a[title]");
        for (Element el : els) {
            linkSet.add(el.attr("href"));
        }
        Elements els2 = document.select("#news_home .title_news a[title]");
        for (Element el : els2) {
            linkSet.add(el.attr("href"));
        }
        System.out.println("Get sport link success!");
    }

    public static void printSportLink() {
        System.out.println("List sport link");
        System.out.println("--------------------");
        for (String string : linkSet) {
            System.out.println(string);
        }
        System.out.println("--------> " + linkSet.size());
    }

    public static void printListNews() {
        for (int i = 0; i < listNews.size(); i++) {
            System.out.println((i + 1) + " - " + listNews.get(i).getTitle());
        }
    }

    public static News getNewsFromUrl(String url) {
        try {
            Connection cnn = Jsoup.connect(url);
            Document document = cnn.get();

            String time = document.select("#box_details_news .block_timer_share .block_timer").text();
            String title = document.select(".sidebar_1 .title_news_detail").text();
            String description = document.select("#box_details_news .short_intro").text();
            String content = document.select("#box_details_news .fck_detail").text();

            News n = new News();
            n.setId(System.currentTimeMillis());
            n.setTitle(title);
            n.setDescription(description);
            n.setContent(content);
            n.setPublicTime(time);
            System.out.println("Get news success!");
            return n;
        } catch (Exception e) {
            System.out.println("Invalid url.");
            return null;
        }
    }

    public News getNewsFromUrl2(String url) {
        try {
            Connection cnn = Jsoup.connect(url);
            Document document = cnn.get();

            String time = document.select("#box_details_news .block_timer_share .block_timer").text();
            String title = document.select(".sidebar_1 .title_news_detail").text();
            String description = document.select("#box_details_news .short_intro").text();
            String content = document.select("#box_details_news .fck_detail").text();

            News n = new News();
            n.setId(System.currentTimeMillis());// set gia tri luu vao News
            n.setTitle(title);
            n.setDescription(description);
            n.setContent(content);
            n.setPublicTime(time);
            System.out.println(n.getTitle());
            System.out.println("Get news success!");
            return n;
        } catch (Exception e) {
            System.out.println("Invalid url.");
            return null;
        }
    }

}
