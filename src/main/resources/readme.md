Solution readme for Sainsburys webcralwer results test:
------------------------------------------------------

Using:

    Java - Version 8.

    Dependency managment - gradle.

    Dependencies: see build.gradle.

    Dependency Injection - Guice.

    Built from IDE - IntelliJ 2016.1.2.


Build instructions:

    Import as a new gradle project, and bring in the dependencies from the build file. No other special configurations made.


Running instructions:

    > Webscraper

    defaults to use url:

    http://hiring-tests.s3-website-eu-west- 1.amazonaws.com/2015_Developer_Scrape/5_products.html

    Should be also able to pass a url as command line argument.

Notes:

    Had an issue locating the data field description based on value from the example:
    "description":"Great to eat now...."
    Therefore took the description to mean that element on the navigated to product page from parent list.

