package application;

import entities.Product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Program {

    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter full file path: ");
        String path = sc.nextLine();

        try (BufferedReader br = new BufferedReader(new FileReader(path))){

            List<Product> list = new ArrayList<>();

            String line = br.readLine();
            while (line != null){
                String[] fields = line.split(",");
                list.add(new Product(fields[0], Double.parseDouble(fields[1])));
                line = br.readLine();
            }

            double avg = list.stream()  //convert em stream
                    .map(p -> p.getPrice())  //cria outra stream com os preços
                    .reduce(0.0, (x, y) -> x + y) / list.size();  //faz operação de incremento da soma

            System.out.println("Average price: " + String.format("%.2f", avg));

            Comparator<String> comp = Comparator.comparing(String::toUpperCase); //cria comparator

            List<String> names = list.stream() //converte lista em stream
                    .filter(p -> p.getPrice() < avg)  //filtra em preços menores que a media
                    .map(p -> p.getName())  //cria outra stream com preços menores que a media
                    .sorted(comp.reversed())  //repassa comparador invertido
                    .collect(Collectors.toList());  //converte essa stream em list

            names.forEach(System.out::println);

        } catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }
        sc.close();


    }

}
