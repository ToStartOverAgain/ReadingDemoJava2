import java.io.IOException;
import java.util.logging.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class Thu {

  public static void main(String[] args)  {
    try {
      String url = "http://dantri.com.vn/su-kien/thu-truong-nga-my-tim-cach-bao-vay-nga-bang-400-ten-lua-20180303210408423.htm";
      Document doc = Jsoup.connect(url).get();
      Elements paragraphs = doc.select("p");
      for(Element p : paragraphs)
        System.out.println(p.text());
    } 
    catch (IOException ex) {
      Logger.getLogger(JavaApplication14.class.getName())
            .log(Level.SEVERE, null, ex);
    }
  }
}
