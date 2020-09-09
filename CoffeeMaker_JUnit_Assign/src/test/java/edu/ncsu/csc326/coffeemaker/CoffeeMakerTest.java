/*
 * Copyright (c) 2009,  Sarah Heckman, Laurie Williams, Dright Ho
 * All Rights Reserved.
 * 
 * Permission has been explicitly granted to the University of Minnesota 
 * Software Engineering Center to use and distribute this source for 
 * educational purposes, including delivering online education through
 * Coursera or other entities.  
 * 
 * No warranty is given regarding this software, including warranties as
 * to the correctness or completeness of this software, including 
 * fitness for purpose.
 * 
 * 
 * Modifications 
 * 20171114 - Ian De Silva - Updated to comply with JUnit 4 and to adhere to 
 * 							 coding standards.  Added test documentation.
 */
package edu.ncsu.csc326.coffeemaker;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc326.coffeemaker.exceptions.InventoryException;
import edu.ncsu.csc326.coffeemaker.exceptions.RecipeException;

/**
 * Unit tests for CoffeeMaker class.
 * 
 * @author Sarah Heckman
 */
public class CoffeeMakerTest {
	
	/**
	 * The object under test.
	 */
	private CoffeeMaker coffeeMaker;
	
	// Sample recipes to use in testing.
	private Recipe recipe1;
	private Recipe recipe2;
	private Recipe recipe3;
	private Recipe recipe4;

	/**
	 * Initializes some recipes to test with and the {@link CoffeeMaker} 
	 * object we wish to test.
	 * 
	 * @throws RecipeException  if there was an error parsing the ingredient 
	 * 		amount when setting up the recipe.
	 */
	@Before
	public void setUp() throws RecipeException {
		coffeeMaker = new CoffeeMaker();
		
		//Set up for r1
		recipe1 = new Recipe();
		recipe1.setName("Coffee");
		recipe1.setAmtChocolate("0");
		recipe1.setAmtCoffee("3");
		recipe1.setAmtMilk("1");
		recipe1.setAmtSugar("1");
		recipe1.setPrice("50");
		
		//Set up for r2
		recipe2 = new Recipe();
		recipe2.setName("Mocha");
		recipe2.setAmtChocolate("4");
		recipe2.setAmtCoffee("3");
		recipe2.setAmtMilk("1");
		recipe2.setAmtSugar("1");
		recipe2.setPrice("75");
		
		//Set up for r3
		recipe3 = new Recipe();
		recipe3.setName("Latte");
		recipe3.setAmtChocolate("0");
		recipe3.setAmtCoffee("3");
		recipe3.setAmtMilk("3");
		recipe3.setAmtSugar("1");
		recipe3.setPrice("100");
		
		//Set up for r4
		recipe4 = new Recipe();
		recipe4.setName("Hot Chocolate");
		recipe4.setAmtChocolate("4");
		recipe4.setAmtCoffee("0");
		recipe4.setAmtMilk("1");
		recipe4.setAmtSugar("1");
		recipe4.setPrice("65");
		
	}
	
	
	/**
	 * Given a coffee maker with the default inventory
	 * When we add inventory with well-formed quantities
	 * Then we do not get an exception trying to read the inventory quantities.
	 * 
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test
	public void testAddInventory() throws InventoryException {
		coffeeMaker.addInventory("4","7","0","9");
	}
	
	/**
	 * Given a coffee maker with the default inventory
	 * When we add inventory with malformed quantities (i.e., a negative 
	 * quantity and a non-numeric string)
	 * Then we get an inventory exception
	 * 
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test(expected = InventoryException.class)
	public void testAddInventoryException() throws InventoryException {
		coffeeMaker.addInventory("4", "-1", "asdf", "3");
	}
	
	/**
	 * Given a coffee maker with one valid recipe
	 * When we make coffee, selecting the valid recipe and paying more than 
	 * 		the coffee costs
	 * Then we get the correct change back.
	 * @throws RecipeException 
	 */
	@Test
	public void testEditrecipe() throws RecipeException {
		coffeeMaker.addRecipe(recipe2);
		 Recipe r;
		//Set up for r
		r = new Recipe();
		r.setName("esspresso");
		r.setAmtChocolate("0");
		r.setAmtCoffee("5");
		r.setAmtMilk("0");
		r.setAmtSugar("4");
		r.setPrice("45");
		coffeeMaker.editRecipe(0, r);
		assertEquals(55, coffeeMaker.makeCoffee(0, 100));
	}
	
	@Test
	public void testMakeCoffee1() {
		coffeeMaker.addRecipe(recipe1);		
		assertEquals(25, coffeeMaker.makeCoffee(0, 75));
	}
	@Test
	public void testMakeCoffee2() {
		coffeeMaker.addRecipe(recipe1);
		Inventory I = new Inventory();
		coffeeMaker.makeCoffee(0, 100);
		int expected = I.getCoffee() - recipe1.getAmtCoffee();
		assertEquals(expected, I.getCoffee());
	}
	
	@Test
	public void testMakeMocha() {
		coffeeMaker.addRecipe(recipe2);
		assertEquals(25, coffeeMaker.makeCoffee(0, 100));
	}
	@Test
	public void testMakelatte() {
		coffeeMaker.addRecipe(recipe3);
		assertEquals(0, coffeeMaker.makeCoffee(0, 100));
	}
	@Test
	public void testMakeHotChocolate() {
		coffeeMaker.addRecipe(recipe4);
		assertEquals(35, coffeeMaker.makeCoffee(0, 100));
	}
	
	@Test 
	public void testaddSugar1() throws InventoryException {
		coffeeMaker.addInventory("0", "0", "5", "0");
		Inventory I = new Inventory();
		assertEquals(20, I.getSugar());
	}
	@Test 
	public void testaddSugar2() throws InventoryException {
		Inventory I = new Inventory();
		I.addSugar("5");
		assertEquals(20, I.getSugar());
	}
	@Test 
	public void testaddchocolate1() throws InventoryException {
		coffeeMaker.addInventory("0", "0", "0", "5");
		coffeeMaker.checkInventory();
		Inventory I = new Inventory();
		assertEquals(20, I.getChocolate());
	}
	@Test 
	public void testaddchocolate2() throws InventoryException {
		Inventory I = new Inventory();
		I.addChocolate("5");
		assertEquals(20, I.getChocolate());
	}
	@Test 
	public void testAddMilk1() throws InventoryException {
		Inventory I = new Inventory();
		coffeeMaker.addInventory("0", "5", "0", "0");		
		assertEquals(20, I.getMilk());
	}
	@Test 
	public void testAddMilk2() throws InventoryException {
		Inventory I = new Inventory();
		I.addMilk("5");
		assertEquals(20, I.getMilk());
	}
	@Test 
	public void testAddcoffe1() throws InventoryException {
		coffeeMaker.addInventory("5", "0", "0", "0");
		Inventory I = new Inventory();
		assertEquals(20, I.getCoffee());
	}
	@Test 
	public void testAddcoffe2() throws InventoryException {
		Inventory I = new Inventory();
		I.addCoffee("5");
		assertEquals(20, I.getCoffee());
	}

}
