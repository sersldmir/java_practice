package hw_8.Library_classes;

import java.io.Serializable;

public abstract class Library implements Serializable{
    protected String name;
    protected String adress;
    protected String city;
    protected String director_full_name;

    public Library(String library_name, String library_adress, String library_city, String library_director_full_name){
        this.name = library_name;
        this.adress = library_adress;
        this.city = library_city;
        this.director_full_name = library_director_full_name;
    }

    @Override
    public String toString(){
        return "Название библиотеки: " + this.name + "; Находится по адресу: " + this.adress + " в городе " + this.city
         + " под руководством директора с ФИО " + this.director_full_name;
    }
}
