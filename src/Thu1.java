import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Thu1 {
   public static void main(String[] args) throws IOException {
      String url = "https://thethao.vnexpress.net/tin-tuc/giai-ngoai-hang-anh/mourinho-top-4-luon-la-muc-tieu-chinh-cua-man-utd-3717841.html";
      Document document = Jsoup.connect(url).get();
      System.out.println(document.title());
     Elements paragraphs = document.select("p");
      for(Element p : paragraphs)
        System.out.println(p.text());
   }
}
