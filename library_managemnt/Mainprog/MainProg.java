package hw_8.Mainprog;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;
import hw_8.Library_classes.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.ParseException;
import java.io.ObjectInputStream;
//import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;


/* при тестировании на других системах надо поменять пути файлов! */
/* здесь нет implements Serializable, так как он уже присутствует в ArrayList<Matlibrary> */
class LibraryArrayList <T> extends ArrayList<MatLibrary> /*implements Serializable*/{

    LibraryArrayList(){}

    private boolean add_library(MatLibrary lib){
        boolean added = false;
        int num_of_occ = Collections.frequency(this, lib);
        if (num_of_occ==0){
            added = super.add(lib);
        }
        return added;
    }

    @Override
    public boolean add(MatLibrary lib){
        return add_library(lib);
    }

    @Override
    public String toString(){
        System.out.println("Информация об элементах коллекции\n");
        for (MatLibrary element: this){
            element.print_list();
            System.out.println("\n\n");
        }
        return "Информация напечатана!";
    }

    public String get(String lib_name){
        for (MatLibrary el: this){
            if (el.get_lib_name().equals(lib_name)){
                return el.get_lib_name();
            }
        }
        return null;
    }

    public void sort_by_reader_number(){
        Collections.sort(this, (a,b) -> a.get_number_of_readers() - b.get_number_of_readers());
        System.out.println("Отсортированная коллекция по количеству читателей");
        this.toString();
    }

    public void add_info_to_library(String l_name, String b_name_hall, String b_name_reader, String b_book_name, Date b_date_of_handling, int b_number_of_days,
    int b_deposit, int r_number_of_books, int r_floor, int r_office, String reader_first_name,
    String reader_patr, String reader_job_place, String reader_gender,
    int reader_age){
        for (MatLibrary element: this){
            if (element.get_lib_name().equals(l_name)){
                element.add(b_name_hall, b_name_reader, b_book_name, b_date_of_handling, 
                b_number_of_days, b_deposit, r_number_of_books, r_floor, r_office, reader_first_name, 
                reader_patr, reader_job_place, reader_gender, reader_age);
            }
        }
    }

    public void save_collection(String file_name) throws IOException, FileNotFoundException{
        ObjectOutputStream objectOutputStream  = new ObjectOutputStream(new FileOutputStream(file_name));
        objectOutputStream.writeObject(this);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    public void export_collection_db(Connection conn){
        try{
            String insert_sql = "INSERT OR IGNORE INTO Libraries(ID, Name, Address, City, Director) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insert_sql);
            for (MatLibrary el: this){
                pstmt.setInt(1, el.ID());
                pstmt.setString(2, el.lib_name());
                pstmt.setString(3, el.lib_adress());
                pstmt.setString(4, el.lib_city());
                pstmt.setString(5, el.lib_director_full_name());
                pstmt.executeUpdate();
            }
            insert_sql = "INSERT OR IGNORE INTO Readers(ID, First_name, Last_name, Patr, Job_place, Gender, Age, lib) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(insert_sql);
            int help_id = 0;
            for (MatLibrary el_m: this){
                for (Reader el_r: el_m.list_of_readers()){
                    pstmt.setInt(1, help_id);
                    pstmt.setString(2, el_r.get_first_name());
                    pstmt.setString(3, el_r.get_last_name());
                    pstmt.setString(4, el_r.get_patr());
                    pstmt.setString(5, el_r.get_job_place());
                    pstmt.setString(6, el_r.get_gender());
                    pstmt.setInt(7, el_r.get_age());
                    pstmt.setInt(8, el_m.ID());
                    pstmt.executeUpdate();
                    help_id+=1;
                }
            }
            insert_sql = "INSERT OR IGNORE INTO ReadingRooms(ID, Hall_name, Num_of_books, Floor, Office, lib) VALUES (?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(insert_sql);
            help_id = 0;
            for (MatLibrary el_m: this){
                for (ReadingRoom el_rr: el_m.list_of_rooms()){
                    pstmt.setInt(1, help_id);
                    pstmt.setString(2, el_rr.get_hall_name());
                    pstmt.setInt(3, el_rr.get_number_of_books());
                    pstmt.setInt(4, el_rr.get_floor());
                    pstmt.setInt(5, el_rr.get_office());
                    pstmt.setInt(6, el_m.ID());
                    pstmt.executeUpdate();
                    help_id+=1;
                }
            }
            insert_sql = "INSERT OR IGNORE INTO GivingLiterature(ID, Name_hall, Name_reader, Book_name, Date_of_handling, Number_of_days, Deposit, lib) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(insert_sql);
            help_id = 0;
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            for (MatLibrary el_m: this){
                for (GivingLiterature el_gl: el_m.list_of_literature()){
                    pstmt.setInt(1, help_id);
                    pstmt.setString(2, el_gl.get_name_hall());
                    pstmt.setString(3, el_gl.get_name_reader());
                    pstmt.setString(4, el_gl.get_book_name());
                    pstmt.setString(5, df.format(el_gl.get_date_of_handling()));
                    pstmt.setInt(6, el_gl.get_number_of_days());
                    pstmt.setInt(7, el_gl.get_deposit());
                    pstmt.setInt(8, el_m.ID());
                    pstmt.executeUpdate();
                    help_id+=1;
                }
            }
            
            System.out.println("Loading to bd successful!");
            System.out.println("\n\n\n\n\n");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }
    
}


public class MainProg{

    public static LibraryArrayList<MatLibrary> load_collection(String file_name) 
    throws IOException, FileNotFoundException, ClassNotFoundException, ClassCastException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(String.format("VSCode/Java/hw_8/Tests/%s", file_name)));
        try{
            @SuppressWarnings("unchecked")
            LibraryArrayList<MatLibrary> lib_col = (LibraryArrayList<MatLibrary>) objectInputStream.readObject();
            objectInputStream.close(); 
            return lib_col;
        }
        catch (ClassCastException | ClassNotFoundException e){
            System.out.println("Что-то не так с классом!");
            return null;
        }
        catch (IOException ie){
            System.out.println("Что-то не так со стримом");
            return null;
        }
        catch (Exception e){
            System.out.println("Что-то не то совсем!");
            return null;
        }
    }

    public static LibraryArrayList<MatLibrary> import_collection_db(Connection conn) throws ParseException{
        LibraryArrayList<MatLibrary> lib_lst = new LibraryArrayList<MatLibrary>();
        try{
            Statement stmt = conn.createStatement();
            String select_sql_query = "SELECT" + "\n" +
            "L.Name || ';' || L.Address || ';' || L.City || ';' || L.Director || '\n'" + "\n" +
            "|| GL.Name_hall || ';' || GL.Name_reader || ';' || GL.Book_name || ';'" + "\n" +
            "|| GL.Date_of_handling || ';' || GL.Number_of_days" + "\n" +
            "|| ';' || GL.Deposit || ';' || RR.Num_of_books || ';' || RR.Floor || ';' ||" + "\n" +
            "RR.Office || ';' || R.First_name || ';' ||" + "\n" +
            "R.Patr || ';' || R.Job_place || ';' || R.Gender || ';' || R.Age" + "\"Output\"" +
            "FROM Libraries L" + "\n" +
            "JOIN GivingLiterature GL" + "\n" +
            "ON L.ID = GL.lib" + "\n" +
            "JOIN ReadingRooms RR" + "\n" +
            "ON L.ID = RR.lib" + "\n" +
            "JOIN Readers R" + "\n" +
            "ON L.ID = R.lib" + "\n" +
            "GROUP BY GL.ID, GL.lib";
            ResultSet rs = stmt.executeQuery(select_sql_query);
            while (rs.next()){
                String line = rs.getString("Output");
                String[] two_line_argument = line.split("\n");
                String[] lib_args = two_line_argument[0].split(";");
                if (lib_lst.get(lib_args[0])==null){
                    System.out.println("Нет библиотеки! Добавляем!");
                    lib_lst.add(new MatLibrary(lib_args[0], lib_args[1], lib_args[2], lib_args[3]));
                }
                String[] other_args = two_line_argument[1].split(";");
                lib_lst.add_info_to_library(lib_args[0], other_args[0], other_args[1], other_args[2], new SimpleDateFormat("dd.MM.yyyy").parse(other_args[3]),
                Integer.parseInt(other_args[4]), Integer.parseInt(other_args[5]), Integer.parseInt(other_args[6]), 
                Integer.parseInt(other_args[7]), Integer.parseInt(other_args[8]), other_args[9], other_args[10], other_args[11], 
                other_args[12], Integer.parseInt(other_args[13]));
            }
            System.out.println("Loading from db successful!");
            return lib_lst;
            
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Import aborted. Returning empty collection");
            return lib_lst;
        }
        catch(Exception e){
            System.out.println("Import aborted. Returning empty collection");
            return lib_lst;
        }
    }

    public static Connection connect_to_db(){
        String path = "jdbc:sqlite:VSCode/Java/hw_8/Tests/test_lib.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(path);
            System.out.println("Connection to database successful!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, ParseException{
        Class.forName("org.sqlite.JDBC");
        Connection bd_conn = connect_to_db();
        System.out.printf("Добро пожаловать в систему управления коллекций библиотек!\nВ данной версии программа добавляет данные через текстовый файл.\n");
        System.out.printf("По требованию будет добавлена возможность ввода с клавиатуры\n");
        LibraryArrayList<MatLibrary> lib_lst;
        Scanner reader = new Scanner(System.in);
        init_loop: while (true) {
            System.out.println("Выберите номер опции инициализации коллекции");
            System.out.println("1. Загрузить существующую через бинарник");
            System.out.println("2. Создать новую коллекцию");
            System.out.println("3. Загрузить существующую через бд");
            int init_answer = Integer.parseInt(reader.nextLine());
            switch (init_answer){
                case 1:
                    try{
                    System.out.println("Введите название файла с .txt");
                    String f_name = reader.nextLine();
                    lib_lst = load_collection(f_name);
                    System.out.println("\n\n\n\n\n");
                    break init_loop;
                    }
                    catch (Exception e){
                        System.out.println("Произошла ошибка!");
                        System.out.println("\n\n\n\n\n");
                        continue init_loop;
                    }
                case 2:
                    lib_lst = new LibraryArrayList<MatLibrary>();
                    System.out.println("\n\n\n\n\n");
                    break init_loop;
                case 3:
                    lib_lst = import_collection_db(bd_conn);
                    System.out.println("\n\n\n\n\n");
                    break init_loop;
                default:
                    System.out.println("Не та опция!");
                    System.out.println("\n\n\n\n\n");
                    continue init_loop;
            }

        }
        System.out.println("Инициализация завершена успешно!");
        main_loop: while (true){
            try{
                System.out.println("Меню");
                System.out.println("1. Добавить данные");
                System.out.println("2. Вывести информацию");
                System.out.println("3. Отсортировать по посетителям и вывести");
                System.out.println("4. Сохранить в файл");
                System.out.println("5. Сохранить в бд");
                System.out.println("6. Выйти");
                System.out.println("Введите номер опции");
                int answer = Integer.parseInt(reader.nextLine());
                switch (answer){
                    case 1:
                        try{
                            System.out.println("Введите название файла вместе с расширением");
                            String file_name = reader.nextLine();
                            //для тестирования на другой системе изменить путь
                            File data = new File(String.format("VSCode/Java/hw_8/Tests/%s", file_name));
                            Scanner data_reader = new Scanner(data);
                            String[] lib_args = data_reader.nextLine().split(";");
                            if (lib_lst.get(lib_args[0])==null){
                                System.out.println("Нет библиотеки! Добавляем!");
                                lib_lst.add(new MatLibrary(lib_args[0], lib_args[1], lib_args[2], lib_args[3]));
                            }
                            String[] other_args = data_reader.nextLine().split(";");
                            lib_lst.add_info_to_library(lib_args[0], other_args[0], other_args[1], other_args[2], new SimpleDateFormat("dd.MM.yyyy").parse(other_args[3]),
                            Integer.parseInt(other_args[4]), Integer.parseInt(other_args[5]), Integer.parseInt(other_args[6]), 
                            Integer.parseInt(other_args[7]), Integer.parseInt(other_args[8]), other_args[9], other_args[10], other_args[11], 
                            other_args[12], Integer.parseInt(other_args[13]));
                            System.out.println("Данные добавлены успешно!");
                            data_reader.close();
                            System.out.println("\n\n\n\n\n");
                            break;
                        }
                        catch (FileNotFoundException fe){
                            System.out.println("Нет такого файла!");
                            System.out.println("\n\n\n\n\n");
                            continue main_loop;
                        }
                        catch (ParseException pe){
                            System.out.println("Что-то пошло не так!");
                            System.out.println("\n\n\n\n\n");
                            continue main_loop;
                        }
                    case 2:
                        System.out.println(lib_lst);
                        System.out.println("\n\n\n\n\n");
                        break;
                    case 3:
                        lib_lst.sort_by_reader_number();
                        System.out.println("\n\n\n\n\n");
                        break;
                    case 4:
                        try{
                            System.out.println("Введите название файла, в который хотите сохранить коллекцию");
                            String file_name = reader.nextLine();
                            lib_lst.save_collection(String.format("VSCode/Java/hw_8/Tests/%s", file_name));
                            System.out.println("Сохранение успешно!");
                            System.out.println("\n\n\n\n\n");
                            break;
                        }
                        catch (IOException ie) {
                            System.out.println("Запись не удалась!");
                            continue main_loop;
                        }
                    case 5:
                        lib_lst.export_collection_db(bd_conn);
                        break;
                    case 6:
                        System.out.println("Завершаем работу...");
                        reader.close();
                        break main_loop;
                    default:
                        System.out.println("Неверная опция!");
                        System.out.println("\n\n\n\n\n");
                        break;
                }
            }
            catch (NumberFormatException ne){
                System.out.println("Не то введено!");
                System.out.println("\n\n\n\n\n");
            }
        }
    }
}