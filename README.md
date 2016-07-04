Grub example
============

This example generates a basic Microservice Java application built with Gradle.

You can generate the example project by:

    grub generate https://github.com/gonzalad/grub-microservice-archetype --directory my-project

    Once it's generated, you can test it via:

    ```text
    $ cd my-project
    $ ./gradlew bootRun
    ```

    Open your browser http://localhost:8080/swagger-ui.html:

    * select HelloWorld Controller, and call GET /v1 resource.
