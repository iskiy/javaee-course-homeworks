package client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class StarterClient {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(StarterClient.class, args);
//
//        LibraryClass libraryClassByClass = applicationContext.getBean(LibraryClass.class);
//        libraryClassByClass.printInfo();

//        LibraryClass myLibraryClass = (LibraryClass) applicationContext.getBean("myLibraryClass");
//        myLibraryClass.printInfo();
    }
}