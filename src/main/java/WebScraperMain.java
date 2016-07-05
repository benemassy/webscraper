import com.google.inject.Guice;
import com.google.inject.Injector;
import config.GuiceModule;
import controller.GoodsItemController;

public class WebScraperMain {

    public static void main(String[] args) {

        Injector injector = Guice.createInjector(new GuiceModule());

        GoodsItemController controller = injector.getInstance(GoodsItemController.class);

        String goodsSource;

        if(args.length == 0) {
            goodsSource = controller.getDefaultsGoodsSource();
        }
        else{
            goodsSource = args[0];
        }

        try {

            controller.setItemsFromUrl(goodsSource);

            System.out.println(controller.getItemsAsJSon());

        } catch (Exception e) {

            System.out.println("unable to collect or process web site items " + e.getLocalizedMessage());

        }

    }


}
