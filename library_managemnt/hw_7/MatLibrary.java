package hw_7;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class MatLibrary implements Serializable{
    private ArrayList<Reader> list_of_readers;
    private ArrayList<ReadingRoom> list_of_rooms;
    private ArrayList<GivingLiterature> list_of_literature;
    private String lib_name;
    private String lib_adress;
    private String lib_city;
    private String lib_director_full_name;

    MatLibrary(String l_name, String l_adress, String l_city, String l_dir){
        this.lib_name = l_name;
        this.lib_adress = l_adress;
        this.lib_city = l_city;
        this.lib_director_full_name = l_dir;
        this.list_of_literature = new ArrayList<GivingLiterature>();
        this.list_of_readers = new ArrayList<Reader>();
        this.list_of_rooms = new ArrayList<ReadingRoom>();
    }

    public void add(String b_name_hall, String b_name_reader, String b_book_name, Date b_date_of_handling, int b_number_of_days,
    int b_deposit, int r_number_of_books, int r_floor, int r_office, String reader_first_name,
    String reader_patr, String reader_job_place, String reader_gender,
    int reader_age){
        boolean found_last_name = false;
        boolean found_hall_name = false;
        if (this.list_of_readers.size() != 0){
            for (Reader reader_el: this.list_of_readers){
                if (reader_el.get_last_name().equals(b_name_reader)){
                    found_last_name = true;
                    break;
                }
            }
        }
        if (this.list_of_rooms.size() != 0){
            for (ReadingRoom room_el: this.list_of_rooms){
                if (room_el.get_hall_name().equals(b_name_hall)){
                    found_hall_name = true;
                    break;
                }
            }
        }
        if (found_last_name==false){
            this.list_of_readers.add(new Reader(this.lib_name, this.lib_adress, this.lib_city, this.lib_director_full_name, reader_first_name, b_name_reader , reader_patr, reader_job_place, reader_gender, reader_age));
        }
        if (found_hall_name==false){
            this.list_of_rooms.add(new ReadingRoom(this.lib_name, this.lib_adress, this.lib_city, this.lib_director_full_name, b_name_hall, r_number_of_books, r_floor, r_office));
        }
        this.list_of_literature.add(new GivingLiterature(this.lib_name, this.lib_adress, this.lib_city, this.lib_director_full_name, b_name_hall, b_name_reader, b_book_name, b_date_of_handling, b_number_of_days, b_deposit));
    }


    public void print_list(){
        for (GivingLiterature lit_el: this.list_of_literature){
            System.out.println(lit_el);
            System.out.println("Информация о читателе");
            String b_name_hall = lit_el.get_name_hall();
            String b_name_reader = lit_el.get_name_reader();
            for (Reader reader_el: this.list_of_readers){
                if (reader_el.get_last_name().equals(b_name_reader)){
                    System.out.println(reader_el);
                    System.out.println();
                    break;
                }
            }
            System.out.println("Информация о зале и библиотеке");
            for (ReadingRoom room_el: this.list_of_rooms){
                if (room_el.get_hall_name().equals(b_name_hall)){
                    System.out.println(room_el);
                    System.out.println();
                    break;
                }
            }

        }
    }

    public int get_number_of_readers(){
        return this.list_of_readers.size();
    }

    public String get_lib_name(){
        return this.lib_name;
    }

}
