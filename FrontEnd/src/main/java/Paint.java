import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class Paint extends JFrame {
    public static String path="C:/Users/alsam//Documents/GitHub/БДЗ ИП/БДЗ ИП/parser.exe";  // ССЫЛКА НА ПАРСЕР
    public static String path_check="C:/Users/alsam/Documents/GitHub/БДЗ ИП/БДЗ ИП/HTMLdb/BigRussian Federation.txt"; //файл по которому смотрится количество годов(строк)
    public JComboBox country,otnos,absolt,year_start,year_stop;
    public JButton update,save,paint,clear;
    public JPanel panel;
    public String data_name="график";
    public double[] xData = new double[] { 0.0 };
    public double[] yData = new double[] { 2.0 };
    public String[] years;
    public XYChart chart= QuickChart.getChart("Sample Chart", "X", "Y", "y(x)", xData, yData);
    public XChartPanel xchartpanel=new XChartPanel(chart);
    public int width_frame=800,height_frame=600;
    public Read read=new Read();
    public ArrayList<String>  name_countr=new ArrayList<String>();
    public int count_of_years,start,stop;
    public double YEARS[];
    public String cntr,otninf,absinf,yearstrt,yearstp;
    public double[] data1,data2,data3,data4,data5,DATA;
    public double[] year1,year2,year3,year4,year5;
    public JFrame frame;
    public ArrayList<Save> saving = new ArrayList<Save>();
    public String[][]mass;


    public Paint() throws IOException, InterruptedException {
        frame = new JFrame("БДЗ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width_frame,height_frame);


        NonPaint();

        frame.setVisible(true);
    }
    public static void StartParser(String path) throws IOException {

        Runtime.getRuntime().exec(path);

    }
public void NonPaint() throws IOException, InterruptedException {
    StartParser(path);
    Thread.sleep(2000);
    read.CountOfFiles();
    mass=read.DataOfFiles();
    panel = new JPanel();
    panel.setLayout(null);
    chart.removeSeries("y(x)");
    Countries();Texts();OtnosInf();AbsInf();YearStart();YearStop();Buttons();
    xchartpanel.setBounds(150,10,620,540);

        panel.add(xchartpanel);
        frame.add(panel);
}
    public void Countries( ){
        final String[] name_countries=read.names_of_files;
        String[] right_name=new String[name_countries.length/2];//то что нужно
        String name;
        for(int i=0;i<name_countries.length/2;i++){
            name=name_countries[i].replace("Big","");
            name=name.replace(".txt","");
            right_name[i]=name;
        }
        country=new JComboBox(right_name);
        country.setBounds(10,35,120,25);
        country.setBackground(Color.PINK);
        country.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox box = (JComboBox)e.getSource();
                cntr = (String)box.getSelectedItem();
                name_countr.add(cntr);
                otnos.setEnabled(true);
                absolt.setEnabled(true);
                year_start.setEnabled(false);
                year_stop.setEnabled(false);
            }
        });
        panel.add(country);
    }

    public void OtnosInf(){
        String[] otnos_inf = {
                "Abroad/Resident+Non-Resident",
                "Resident/Non-Resident",
                "Non-Resident/Abroad"
        };
        otnos = new JComboBox(otnos_inf);
        otnos.setEnabled(false);
        otnos.setBounds(10,100,120,25);
        otnos.setBackground(Color.PINK);
        otnos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox box = (JComboBox)e.getSource();
               data_name= otninf = (String)box.getSelectedItem();
                absolt.setEnabled(false);
                year_start.setEnabled(true);

                absinf=null;
            }
        });
        panel.add(otnos);
    }

    public void AbsInf(){
        String[] abs = {
                "Patent",
                "Trademark",
                "IndDes",
                "GDP",
                "Resident",
                "Non-Resident",
                "Abroad"
        };
        absolt = new JComboBox(abs);
        absolt.setEnabled(false);
        absolt.setBounds(10,165,120,25);
        absolt.setBackground(Color.PINK);
        absolt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox box = (JComboBox)e.getSource();
              data_name= absinf = (String)box.getSelectedItem();
                otninf=null;
                otnos.setEnabled(false);
                year_start.setEnabled(true);
                //year_stop.setEnabled(true);*/
            }
        });
        panel.add(absolt);
    }
    public void YearStart()throws IOException {
        int year=2007;
        File f=new File(path_check);//////////////////////НЕ УДАЛЯТЬ ФАЙЛ ИЗ ПАПКИ
         count_of_years=read.getStringCount(f)-2;
         YEARS=new double[count_of_years];
        years=new String[count_of_years];
        for(int i=0;i<count_of_years;i++){
            YEARS[i]=year;
            years[i]=String.valueOf(year);
            year++;
        }
        year_start = new JComboBox(years);
        year_start.setEnabled(false);
        year_start.setBounds(10,230,120,25);
        year_start.setBackground(Color.PINK);
        year_start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox box = (JComboBox)e.getSource();
                yearstrt= (String)box.getSelectedItem();
                start=Integer.parseInt(yearstrt);
                year_stop.setEnabled(true);
            }
        });
        panel.add(year_start);
    }
    public void YearStop(){
        year_stop= new JComboBox(years);
        year_stop.setEnabled(false);
        year_stop.setBounds(10,305,120,25);
        year_stop.setBackground(Color.PINK);

        year_stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox box = (JComboBox)e.getSource();
                yearstp = (String)box.getSelectedItem();
                stop=Integer.parseInt(yearstp);


                 paint.setEnabled(true);
            }
        });
        panel.add(year_stop);
    }

    public void Buttons()throws IOException{
        paint = new JButton("Рисовать");
        paint.setBounds(10,470,120,25);
        paint.setBackground(Color.WHITE);
        paint.setEnabled(false);
        paint.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                otnos.setEnabled(false);
                absolt.setEnabled(false);
                year_start.setEnabled(false);
                year_stop.setEnabled(false);

                if(cntr.contains("United Kingdom")){
                        PaintGraphic(data1,year1);
            }else if(cntr.contains("United States of America")){PaintGraphic(data2,year2);}
            else if(cntr.contains("Russian Federation")){
                    PaintGraphic(data3,year3);
                }
                else if(cntr.contains("Czech Republic")){
                    PaintGraphic(data4,year4);
                }
                else if(cntr.contains("Argentina")){
                    PaintGraphic(data5,year5);
                }
            }
        });
        panel.add(paint);

        clear = new JButton("Стереть");
        clear.setForeground(Color.WHITE);
        clear.setBounds(10,520,120,25);
        clear.setBackground(Color.black);
        panel.add(clear);clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for(int i=0;i<name_countr.size();i++){
                    chart.removeSeries(name_countr.get(i));}

                xchartpanel.repaint();
                year_start.setEnabled(false);
                year_stop.setEnabled(false);
            }
        });


        save= new JButton("Сохранить");
        save.setForeground(Color.black);
        save.setBounds(10,420,120,25);
        save.setBackground(Color.red);
        panel.add(save);
        String path="./Save/"; //
        path+="save.png";
        final File save_graphic=new File(path);
        if(!save_graphic.exists()){
            save_graphic.createNewFile();
        }
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Writer writer = null;
                try {
                    writer = new FileWriter("./Save/yourfile.csv");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).build();
                try {
                    beanToCsv.write(saving);
                    BitmapEncoder.saveBitmap(chart,"./Save/save" ,BitmapEncoder.BitmapFormat.PNG);
                } catch (CsvDataTypeMismatchException e1) {
                    e1.printStackTrace();
                } catch (CsvRequiredFieldEmptyException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                try {
                    writer.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                saving.clear();
            }
        });

        update= new JButton("Обновить");
        update.setForeground(Color.black);
        update.setBounds(10,370,120,25);
        update.setBackground(Color.CYAN);
        panel.add(update);
        update.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel.removeAll();
                panel.repaint();
                frame.remove(panel);
                repaint();

                try {
                    NonPaint();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
    private static CellProcessor[] getProcessors() {
        return new CellProcessor[]{
                new UniqueHashCode(),
                new NotNull(),
                new Optional(),
                new Optional()
        };
    }
    public int NameCountry(String country,String parametr){
        int end=-1;
        if(parametr=="Abroad/Resident+Non-Resident"||parametr=="Resident/Non-Resident"||parametr== "Non-Resident/Abroad"||parametr=="Resident"||parametr== "Non-Resident"||parametr=="Abroad"){
            for(int i=0;i<read.names_of_files.length;i++){
                String str="Small"+country;
                if(read.names_of_files[i].contains(str)){
                    end=i;break;
                }
            }
        }else
        if(parametr=="Patent"||parametr=="Trademark"||parametr== "IndDes"||parametr=="GDP"){
            for(int i=0;i<read.names_of_files.length;i++){
                String str="Big"+country;
                if(read.names_of_files[i].contains(str)){
                    end=i;break;
                }
            }
        }
        return end;
    }

public void PaintGraphic(double[] data,double[] year){
    if(otninf!=null) {
        int k=NameCountry(cntr,otninf);
        double[] one=read.DataColumn(read.SplitEachMass(mass[k]),"Abroad");
        double[] two=read.DataColumn(read.SplitEachMass(mass[k]),"Resident");
        double[] three=read.DataColumn(read.SplitEachMass(mass[k]),"Non-Residen");
        if (otninf == "Abroad/Resident+Non-Resident") {
            data= read.Calculator(one,two,three);
            for(int i=0;i<read.YearsData(data,start,stop).length;i++){
            Save s=new Save();
            s.setNameCountry(cntr);
            s.setData(read.YearsData(data,start,stop)[i]);
            s.setYears(read.Years(start,stop)[i]);
            s.setName_data(otninf);
            saving.add(s);}
            year=read.Years(start,stop);
            chart.addSeries(cntr,read.Years(start,stop), read.YearsData(data,start,stop));
            chart.setTitle(otninf);
            xchartpanel.repaint();
        }
        if (otninf == "Resident/Non-Resident") {
           data= read.Calculator(two,three);
            year=read.Years(start,stop);
            for(int i=0;i<read.YearsData(data,start,stop).length;i++){
                Save s=new Save();
                s.setNameCountry(cntr);
                s.setData(read.YearsData(data,start,stop)[i]);
                s.setYears(read.Years(start,stop)[i]);
                s.setName_data(otninf);
                saving.add(s);
            }
            chart.addSeries(cntr,read.Years(start,stop), read.YearsData(data,start,stop));
            chart.setTitle(otninf);
            xchartpanel.repaint();
        }
        if (otninf ==  "Non-Resident/Abroad") {
             data= read.Calculator(three,one);
            year=read.Years(start,stop);
            for(int i=0;i<read.YearsData(data,start,stop).length;i++){
                Save s=new Save();
                s.setNameCountry(cntr);
                s.setData(read.YearsData(data,start,stop)[i]);
                s.setYears(read.Years(start,stop)[i]);
                s.setName_data(otninf);
                saving.add(s);}
            chart.addSeries(cntr,read.Years(start,stop), read.YearsData(data,start,stop));
            chart.setTitle(otninf);
            xchartpanel.repaint();
        }
    }else if(absinf!=null){
        int k=NameCountry(cntr,absinf);
         data= read.DataColumn(read.SplitEachMass(mass[k]),absinf);
         year=read.Years(start,stop);
        for(int i=0;i<read.YearsData(data,start,stop).length;i++){
            Save s=new Save();
            s.setNameCountry(cntr);
            s.setData(read.YearsData(data,start,stop)[i]);
            s.setYears(read.Years(start,stop)[i]);
            s.setName_data(absinf);
            saving.add(s);}
        chart.addSeries(cntr,read.Years(start,stop), read.YearsData(data,start,stop));
        chart.setTitle(absinf);
        xchartpanel.repaint();
    }
}

    public void Test(int k){
            ArrayList<String[]> list=new ArrayList<String[]>();
            int size=read.SplitEachMass(mass[k]).size();
            for(int i=0;i<size;i++){
                list.add(read.SplitEachMass(mass[k]).get(i));
            }
            for(int i=0;i<size;i++)
            {
                for(int j=0;j<list.get(i).length;j++){
                    System.out.print(list.get(i)[j]+" ");
                }
                System.out.println();
            }
    }
    public void Texts(){
        JTextField text=new JTextField(15);
        text.setBounds(10,10,120,25);
        text.setText("Страны");
        text.setEditable(false);

        JTextField text2=new JTextField(15);
        text2.setBounds(10,75,120,25);
        text2.setText("Относ. данные");
        text2.setEditable(false);

        JTextField text3=new JTextField(15);
        text3.setBounds(10,140,120,25);
        text3.setText("Абс. данные");
        text3.setEditable(false);

        JTextField text4=new JTextField(15);
        text4.setBounds(10,205,120,25);
        text4.setText("Год начала");
        text4.setEditable(false);

        JTextField text5=new JTextField(15);
        text5.setBounds(10,280,120,25);
        text5.setText("Год окончания");
        text5.setEditable(false);

        panel.add(text);panel.add(text2);panel.add(text3);panel.add(text4);panel.add(text5);
    }
}
