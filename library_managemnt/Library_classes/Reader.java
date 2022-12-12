package hw_8.Library_classes;

public class Reader extends Library{
    protected String first_name;
    protected String last_name;
    protected String patr;
    protected String job_place;
    protected String gender;
    protected int age;

    public Reader(String library_name, String library_adress, String library_city, String library_director_full_name, 
    String reader_first_name, String reader_last_name, String reader_patr, String reader_job_place, String reader_gender,
    int reader_age){
        super(library_name, library_adress, library_city, library_director_full_name);
        this.first_name = reader_first_name;
        this.last_name = reader_last_name;
        this.patr = reader_patr;
        this.job_place = reader_job_place;
        this.gender = reader_gender;
        this.age = reader_age;
    }

    @Override
    public String toString(){
        return "ФИО: " + this.first_name + " " + this.last_name + " " + this.patr + "; Место работы: " + 
        this.job_place + "; Пол: " + this.gender + "; Возраст: " + this.age;
    }

    public String get_last_name(){
        return this.last_name;
    }

    public String get_first_name(){
        return this.first_name;
    }

    public String get_patr(){
        return this.patr;
    }

    public String get_job_place(){
        return this.job_place;
    }

    public String get_gender(){
        return this.gender;
    }

    public int get_age(){
        return this.age;
    }

}
