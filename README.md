### How to build the image of the app
```shell
./gradlew bootBuildImage --imageName=maciejmurawski/inpost-interview 
```

### How to run the app (as a docker container)
```shell
docker run -p 8080:8080 maciejmurawski/inpost-interview 
```

### How to run the app (as a standalone app, locally)
```shell
./gradlew clean bootRun
```

### How to run tests
```shell
./gradlew clean test
```

### Swagger
After running the app swagger UI is available for testing.
[Link to swagger](http://localhost:8080/swagger-ui/index.html) 

### Notes

 - There can only be one discount for a given product and count (discount type doesn't matter). 
So there cannot be two discounts (5 USD and 5%) for a given product count at the same time. 
It should prevent the shop owner from having too many or conflicting discounts which can result significant losses
 - When creating percentage discount quantity needs to be between 0 and 1 (should be a fraction)
 - `Policies should be configurable.` - made a CRUD API for policies
 - App uses h2 database in both tests and production. 
If somebody wants to switch to some other SQL database I prepared liquibase migrations.
In tests, I used a library for clearing the database contents between tests. The time and performance is not significant
at the moment, but it gives the separation between tests. 
 - Sample data are loaded through liquibase from csv files in `resources/init`
 - For simplicity, I skipped implementing auth/security/i18n 
 - I added the product and gave it more detail than UUID (name, price etc.) to be more of a real life example
 - I created a separate package for products and tied products and discount policy through productId. 
On purpose DiscountPolicy entity doesn't have a reference to a Product. In the real world app there 
would be a separate microservice that is responsible for exposing a product catalog, and we would ask it 
for the products' data. My purpose here with creating this package was to ease the migration to this step
