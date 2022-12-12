package hw_8.Library_classes;

public class ReadingRoom extends Library{
    protected String hall_name;
    protected int number_of_books;
    protected int floor;
    protected int office;

    public ReadingRoom(String library_name, String library_adress, String library_city, String library_director_full_name,
    String r_hall_name, int r_number_of_books, int r_floor, int r_office){
        super(library_name, library_adress, library_city, library_director_full_name);
        this.hall_name = r_hall_name;
        this.number_of_books = r_number_of_books;
        this.floor = r_floor;
        this.office = r_office;
    }

    @Override
    public String toString(){
        return "Название зала: " + this.hall_name + "; Расположен на " + this.floor + " этаже в кабинете " + this.office + 
        "; имеет " +  this.number_of_books + " книг; " + "Принадлежит библиотеке со следующей информацией: \n" + super.toString();
    }
    
    public String get_hall_name(){
        return this.hall_name;
    }

    public int get_number_of_books(){
        return this.number_of_books;
    }

    public int get_floor(){
        return this.floor;
    }

    public int get_office(){
        return this.office;
    }

}