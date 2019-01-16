package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import model.entities.Product;

public class Program {

	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the full path of the file:");
		String path = sc.nextLine();
		List<Product> products = new ArrayList<>();
		try(BufferedReader br = new BufferedReader(new FileReader(path))){
			
			String line = br.readLine();
			while(line != null) {
				String[] vect = line.split(",");
				products.add(new Product(vect[0], Double.parseDouble(vect[1])));
				line = br.readLine();
			}
			
			
			double avgPrice = products.stream()
					.map(p -> p.getPrice())
					.reduce(0.0, (x, y) -> x + y) / products.size();
			
			System.out.println("Average price: " + String.format("%.2f", avgPrice));
			
			Comparator<String> comp = (x, y) -> x.toUpperCase().compareTo(y.toUpperCase());
			
			List<String> names = products.stream(
					).filter(p -> p.getPrice() < avgPrice)
					.map(p -> p.getName())
					.sorted(comp.reversed()).collect(Collectors.toList());
			
			System.out.println();
			
			names.forEach(System.out::println);
			
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			sc.close();
		}

	}

}
