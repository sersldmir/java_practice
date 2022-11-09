import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

abstract class Toy{
    protected int price;
    protected String material;
    protected String name;
    
    Toy(int toy_price, String toy_material, String toy_name){
        this.price = toy_price;
        this.material = toy_material;
        this.name = toy_name;
    }

    public int get_price(){
        return this.price;
    }

    @Override
    public String toString(){
        return String.format("Название игрушки: %s, сделана из %s, стоит %d рублей", this.name, this.material, this.price);
    }
}



class BigCar extends Toy{
    private final String size = "большой";
    private int user_age;

    BigCar(int toy_price, String toy_material,String toy_name, int t_user_age) throws UserAgeException{
        super(toy_price, toy_material, toy_name);
        if (t_user_age>18){
            throw new UserAgeException("Слишком стар");
        }
        else{
            user_age = t_user_age;
        }
        
    }

    @Override
    public String toString(){
        return super.toString() + "; " + String.format("Размер: %s. Пользуется ребенок %d лет", this.size, this.user_age);
    }
}

class AveragePuzzle extends Toy{
    private final String size = "средний";
    private int user_age;

    AveragePuzzle(int toy_price, String toy_material, String toy_name, int t_user_age) throws UserAgeException{
        super(toy_price, toy_material, toy_name);
        if (t_user_age>18){
            throw new UserAgeException("Слишком стар");
        }
        else if (t_user_age<6 && t_user_age>4){
            throw new UserAgeException("Слишком буйный для такого");
        }
        else{
            user_age = t_user_age;
        }
        
    }

    @Override
    public String toString(){
        return super.toString() + "; " + String.format("Размер: %s. Пользуется ребенок %d лет", this.size, this.user_age);
    }
}

class SmallFigures extends Toy{
    private final String size = "маленький";
    private int user_age;

    SmallFigures(int toy_price, String toy_material, String toy_name, int t_user_age) throws UserAgeException{
        super(toy_price, toy_material, toy_name);
        if (t_user_age>18){
            throw new UserAgeException("Слишком стар");
        }
        else if (t_user_age<3){
            throw new UserAgeException("Слишком маленький");
        }
        else{
            user_age = t_user_age;
        }
        
    }

    @Override
    public String toString(){
        return super.toString() + "; " + String.format("Размер: %s. Пользуется ребенок %d лет", this.size, this.user_age);
    }
}

class PlayRoom{
    private int budget;
    private ArrayList<Toy> toys;

    PlayRoom(int room_budget){
        this.budget = room_budget;
        this.toys = new ArrayList<Toy>();
    }

    public void add(Toy a_toy) throws BudgetException{
        if (this.budget - a_toy.price<0){
            throw new BudgetException("Нет денег на добавление!");
        }
        else{
            this.toys.add(a_toy);
            this.budget-=a_toy.price;
        }
    }

    public void print_info(){
        for (Toy el_toy: this.toys){
            System.out.println(el_toy);
        }
    }

    public void sort_by_price(){
        Collections.sort(this.toys, new ToyComparator());
        System.out.println("Список игрушек отсортирован по цене!");
    }

    public void get_by_price_interval(int lower_bound, int upper_bound){
        System.out.printf("Игрушки в интервале цены (%d; %d)\n", lower_bound, upper_bound);
        for (Toy el_toy: this.toys){
            if (el_toy.price < upper_bound && el_toy.price>lower_bound){
            System.out.println(el_toy);
            }
        }
    }
}


public class kr_mir {
    public static void main(String[] args) {
        try{
            // изменить путь, если тестируется на другой системе
            File test = new File("VSCode/Java/kr_test.txt");
            Scanner reader = new Scanner(test);
            PlayRoom pl_r = new PlayRoom(100);
            int counter=0;
            while (reader.hasNextLine()){
                String[] arg = reader.nextLine().split(";");
                if (counter==0 || counter==1 || counter==6){
                    try{
                        BigCar b_car = new BigCar(Integer.parseInt(arg[0]), arg[1], arg[2], Integer.parseInt(arg[3]));
                        pl_r.add(b_car);
                    }
                    catch (UserAgeException ue){
                        System.out.println(ue.getMessage());
                        counter+=1;
                        continue;
                    }
                    catch (BudgetException be){
                        System.out.println(be.getMessage());
                        counter+=1;
                        continue;
                    }
                }
                else if (counter==2 || counter==3){
                    try{
                        AveragePuzzle av_puz = new AveragePuzzle(Integer.parseInt(arg[0]), arg[1], arg[2], Integer.parseInt(arg[3]));
                        pl_r.add(av_puz);
                    }
                    catch (UserAgeException ue){
                        System.out.println(ue.getMessage());
                        counter+=1;
                        continue;
                    }
                    catch (BudgetException be){
                        System.out.println(be.getMessage());
                        counter+=1;
                        continue;
                    }
                }
                else if (counter==4 || counter==5){
                    try{
                        SmallFigures s_f = new SmallFigures(Integer.parseInt(arg[0]), arg[1], arg[2], Integer.parseInt(arg[3]));
                        pl_r.add(s_f);
                    }
                    catch (UserAgeException ue){
                        System.out.println(ue.getMessage());
                        counter+=1;
                        continue;
                    }
                    catch (BudgetException be){
                        System.out.println(be.getMessage());
                        counter+=1;
                        continue;
                    }
                }
                counter+=1;
            }
            reader.close();
            System.out.println();
            System.out.println("Игровая комната:");
            pl_r.print_info();
            pl_r.sort_by_price();
            System.out.println();
            System.out.println("Отсортированная игровая комната:");
            pl_r.print_info();
            System.out.println();
            pl_r.get_by_price_interval(20, 50);
        }
        catch (FileNotFoundException fe){
            System.out.println("File not found!!!");
        }
    }
}
