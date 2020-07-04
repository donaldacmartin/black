module scot.martin.black.rss {
    requires spring.context;
    requires spring.beans;
    requires scot.martin.black.model;
    requires java.xml.bind;

    exports scot.martin.black.rss.builder;
    exports scot.martin.black.rss.model;
}
