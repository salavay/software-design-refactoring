package ru.akirakozov.sd.refactoring.utils;

import ru.akirakozov.sd.refactoring.model.Product;

public class HtmlUtils {

    public static String buildHtml(String body, String header) {
        return "<html><body>\n" +
                (header != null
                        ?
                        header + "\n"
                        : "") +
                body +
                "</body></html>";
    }

    public static String buildHtml(String body) {
        return buildHtml(body, null);
    }

    public static String productToHtml(Product product) {
        if (product == null) {
            return "";
        }
        return product.getName() + "\t" + product.getPrice() + "</br>\n";
    }
}
