package org.example;

import org.example.exception.*;
import org.testng.Assert;
import org.testng.annotations.*;

public class ManWomanTest {

    private Man man = null;
    private Woman woman = null;
    private Man retiredMan = null;
    private Woman retiredWoman = null;


    @BeforeMethod
    public void beforeMethod() throws Exception {
        man = new Man("John", "Smith", 23);
        woman = new Woman("Mary", "Adams", 21);
        retiredMan = new Man("Tom", "Cat", 66);
        retiredWoman = new Woman("Jerry", "Mouse", 61);
    }

    @AfterMethod
    public void afterMethod() {
        man = null;
        woman = null;
        retiredWoman = null;
        retiredMan = null;

    }

    @Test
    public void testCheckInitialManDataPositive() {
        Assert.assertEquals(man.getFirstName(), "John", "First Name is incorrect");
        Assert.assertEquals(man.getLastName(), "Smith", "Last Name is incorrect");
        Assert.assertEquals(man.getAge(), 23, "Age is incorrect");
}
    @Test
    public void testCheckInitialWomanDataPositive() {
        Assert.assertEquals(woman.getFirstName(), "Mary", "First Name is incorrect");
        Assert.assertEquals(woman.getLastName(), "Adams", "Last Name is incorrect");
        Assert.assertEquals(woman.getAge(), 21, "Age is incorrect");
    }

    @Test
    public void testPersonFirstNameUpdate() throws Exception {
        String newName = "Peter";
        man.setFirstName(newName);
        Assert.assertEquals(man.getFirstName(), newName, "New name does not match" );
    }

    @Test(expectedExceptions = NullFirstNameException.class)
    public void testPersonSetNullFirstName() throws Exception {
        man.setFirstName(null);
    }

    @Test(expectedExceptions = NullFirstNameException.class)
    public void testCreatePersonNoFirstName() throws Exception {
        new Man(null, "Smith", 23);
    }

    @Test
    public void testPersonLastNameUpdate() throws Exception {
        String newLastName = "Jackson";
        woman.setLastName(newLastName);
        Assert.assertEquals(woman.getLastName(), newLastName, "New Lastname does not match" );
    }

    @Test(expectedExceptions = NullLastNameException.class)
    public void testPersonSetNullLastName() throws Exception {
        woman.setLastName(null);
    }

    @Test(expectedExceptions = NullLastNameException.class)
    public void testCreatePersonNoLastName() throws Exception {
        new Woman("Alice", null, 23);
    }

    @Test
    public void testPersonCorrectAgeUpdate() throws Exception {
        int newAge = 42;
        woman.setAge(newAge);
        Assert.assertEquals(woman.getAge(), newAge, "New Age does not match" );
    }

    @Test(expectedExceptions = IncorrectAgeValueException.class)
    public void testPersonNegativeAge() throws Exception {
        woman.setAge(-1);
    }

    @Test(expectedExceptions = IncorrectAgeValueException.class)
    public void testPersonAgeIsMoreThanExpected() throws Exception {
        woman.setAge(151);
    }

    @Test
    public void testManIsRetired() {
        Assert.assertTrue(retiredMan.isRetired(), "Incorrect retirement status");
    }

    @Test
    public void testManIsNotRetired() {
        Assert.assertFalse(man.isRetired(), "Incorrect retirement status");
    }

    @Test
    public void testWomanIsRetired() {
        Assert.assertTrue(retiredWoman.isRetired(), "Incorrect retirement status");
    }

    @Test
    public void testWomanIsNotRetired() {
        Assert.assertFalse(woman.isRetired(), "Incorrect retirement status");
    }

    @Test
    public void testRegisterMarriage() throws Exception {
        Person.registerMarriage(man, woman);
        Assert.assertTrue(man.isMarried(), "Incorrect marital status");
        Assert.assertTrue(woman.isMarried(), "Incorrect marital status");
        Assert.assertEquals(woman.getLastName(), man.getLastName(), "Last Name is not updated");
    }

    @Test(expectedExceptions = MarriageCannotBeRegisteredInvalidStatusException.class)
    public void testMarriedPersonCannotBeMarriedSecondTime() throws Exception {
        Person.registerMarriage(man, woman);
        Person.registerMarriage(man, retiredWoman);
    }

    @Test(expectedExceptions = MarriageCannotBeRegisteredGenderException.class)
    public void testPeopleOfTheSameGenderCannotBeMarried() throws Exception {
        Person.registerMarriage(woman, retiredWoman);
    }

    @Test(expectedExceptions = MarriageCannotBeRegisteredSelfMarriageException.class)
    public void testPersonCannotMarryTheirself() throws Exception {
        Person.registerMarriage(man, man);
    }

    @Test(expectedExceptions = MarriageCannotBeRegisteredAgeException.class)
    public void testPeopleUnder18CannotBeMarried() throws Exception {
        man.setAge(10);
        Person.registerMarriage(man, woman);
    }

    @Test
    public void testRegisterDivorceAndChangeWomanLastName() throws Exception {
        Person.registerMarriage(man, woman);
        Person.registerDivorce(man, woman, true);
        Assert.assertFalse(man.isMarried(), "Incorrect marital status");
        Assert.assertFalse(woman.isMarried(), "Incorrect marital status");
        Assert.assertEquals(woman.getLastName(), woman.originalLastName, "Last Name is not updated");
    }

    @Test
    public void testRegisterDivorceAndDoNotChangeWomanLastName() throws Exception {
        Person.registerMarriage(man, woman);
        Person.registerDivorce(man, woman, false);
        Assert.assertFalse(man.isMarried(), "Incorrect marital status");
        Assert.assertFalse(woman.isMarried(), "Incorrect marital status");
        Assert.assertEquals(woman.getLastName(), man.getLastName(), "Last Name is incorrect");
    }

    @Test(expectedExceptions = DivorceCannotBeProcessedException.class)
    public void testUnmarriedPeopleCannotBeDivorced() throws Exception {
        Person.registerDivorce(man, woman, true);
    }

}
