# Book Order API Project with Spring Boot

## This application can be run in 3 ways:

-Either Use Docker image and run , image created on local maven build as well <br />
-Use Spring Boot run as java application <br />
-User java -jar option to run the jar <br />

## There 3 model classes Book,Order and Discount

-Book crud operation available in BookCRUDController <br />
-Discount crud operation available in DiscountCRUDController <br />
-Book and Discount have many to many relation <br />
-ISBN must be unique for all Books <br />
-Discount model must always have unique bookType/couponCode combination <br />
example:  discount with couponcode/booktype : Discount like this  COM10/COMIC : 10% and COM10/COMIC :25% will not be allowed insert in Discount table

## Open API Swagger Document link <br />

-Open API swagger dock link available on this: http://localhost:8080/swagger-ui/index.html<br />



