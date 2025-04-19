import org.example.entity.Friend;
import org.example.entity.Owner;
import org.example.entity.Pet;
import org.example.presentation.*;
import org.example.repository.OwnerRepository;
import org.example.service.OwnerService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;

import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


import java.time.LocalDateTime;


@Testcontainers
public class TestHibernate {

    @Container
    private static final PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:latest")
                    .withDatabaseName("postgres")
                    .withUsername("postgres")
                    .withPassword("12345")
                    .withInitScript("schema.sql");

    private static SessionFactory sessionFactory;

    @BeforeEach
    public void setUp() {
        Configuration configuration = new Configuration()
                .configure("hibernate.cfg.xml")
                .setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
                .setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")
                .setProperty("hibernate.connection.url", postgres.getJdbcUrl())
                .setProperty("hibernate.connection.username", postgres.getUsername())
                .setProperty("hibernate.connection.password", postgres.getPassword())
                .setProperty("hibernate.hbm2ddl.auto", "update");
        sessionFactory = configuration.buildSessionFactory();

    }

    @Test
    public void checkAdd() {
        Owner owner = Owner.builder()
                .id(141)
                .name("231DIY").build();
        Pet pet = Pet.builder()
                .id(141)
                .name("GGGDOG")
                .birthday(LocalDateTime.now().toString())
                .owner(owner)
                .build();
        Session session = sessionFactory.openSession();
        var transaction = session.beginTransaction();
        session.persist(owner);
        session.persist(pet);
        transaction.commit();
        Assertions.assertNotNull(session.find(Owner.class, 141));
        Assertions.assertNotNull(session.find(Pet.class, 141));

    }


    @Test
    public void checkListPet() {
        Session session = sessionFactory.openSession();
        var transaction = session.beginTransaction();
        Owner owner = Owner.builder()
                .id(77)
                .name("231DIY").build();
        Pet pet = Pet.builder()
                .id(77)
                .name("GGGDOG")
                .birthday(LocalDateTime.now().toString())
                .owner(owner)
                .build();
        session.persist(owner);
        session.persist(pet);
        var owners = session.get(Owner.class, 77);
        var res = owners.getPets();
        System.out.println(res);
        transaction.commit();
    }

    @Test
    public void checkManyToMany() {
        Session session = sessionFactory.openSession();
        var transaction = session.beginTransaction();
        Owner owner = Owner.builder()
                .id(1001)
                .name("231DIY").build();
        Pet pet = Pet.builder()
                .id(1001)
                .name("GGGDOG")
                .birthday(LocalDateTime.now().toString())
                .owner(owner)
                .build();
        session.persist(owner);
        session.persist(pet);
        Friend friend = Friend.builder()
                .id(1111)
                .name("FUU")
                .build();
        session.save(friend);
        var pets = session.get(Pet.class, 1001);
        pets.addFriend(friend);
        transaction.commit();
    }


    @Test
    public void checkAddOwner() {
        Owner owner = Owner.builder()
                .id(10111)
                .name("WER")
                .birthday(LocalDateTime.now().toString())
                .build();
        Configuration configuration = new Configuration();
        configuration.configure();
        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();
            var ownerController = new OwnerService(new OwnerRepository());
            ownerController.save(owner);
            session.getTransaction().commit();
        }
    }

    @Test
    public void checkParser() throws Exception {

        var deleteAllParseOwner = new DeleteAllParseOwner(null);
        var deleteAllParsePet = new DeleteAllParsePet(deleteAllParseOwner);
        var deleteByIdOwner = new DeleteByIdParseOwner(deleteAllParsePet);
        var deleteByIdPet = new DeleteByIdParsePet(deleteByIdOwner);
        var getAllOwner = new GetAllParseOwner(deleteByIdPet);
        var getAllPet = new GetAllParsePet(getAllOwner);
        var getByIdOwner = new GetByIdParseOwner(getAllPet);
        var getByIdPet = new GetByIdParsePet(getByIdOwner);
        var savePet = new SaveParsePet(getByIdPet);
        var saveOwner = new SaveParseOwner(savePet);
        saveOwner.parse("save pet 2351 DOG 12.34.12");


    }
}
