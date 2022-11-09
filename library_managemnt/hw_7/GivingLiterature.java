package hw_7;
import java.util.Date;
import java.text.SimpleDateFormat;

public class GivingLiterature extends Library{
    protected String name_hall;
    protected String name_reader;
    protected String book_name;
    protected Date date_of_handing;
    protected int number_of_days;
    protected int deposit;

    GivingLiterature(String library_name, String library_adress, String library_city, String library_director_full_name,
    String b_name_hall, String b_name_reader, String b_book_name, Date b_date_of_handling, int b_number_of_days,
    int b_deposit){
        super(library_name, library_adress, library_city, library_director_full_name);
        this.name_hall = b_name_hall;
        this.name_reader = b_name_reader;
        this.book_name = b_book_name;
        this.date_of_handing = b_date_of_handling;
        this.number_of_days = b_number_of_days;
        this.deposit = b_deposit;
    }

    @Override
    public String toString(){
        return "Название книги: " + this.book_name + "; Принадлежит человеку с фамилией " + this.name_reader +
        "; Из зала " + this.name_hall + "; Дата выдачи: " + new SimpleDateFormat("dd-MM-yyyy").format(this.date_of_handing) + "; Выдана на " + this.number_of_days +
        " дней; " + "Залог: " + this.deposit;
    }

    public String get_name_hall(){
        return name_hall;
    }
    public String get_name_reader(){
        return name_reader;
    }

}