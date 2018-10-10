import java.io.*;
import java.util.ArrayList;

public class Read {
    /*
    нам нужно узнать количество файлов в папке с txt
     */
    public String path_directory="C:/Users/alsam/Documents/GitHub/БДЗ ИП/БДЗ ИП/HTMLdb"; //ДИРЕКТОРИЯ СО ВСЕМИ ФАЙЛАМИ (ПАПКА ДИМАСА НА РАБОЧЕМ СТОЛЕ, куда он TXT СКИДЫВАЕТ)
    public int count_of_files;
    public String[] names_of_files;
    public String[][] data;

   public int CountOfFiles(){
       File fileList = new File(path_directory);
       File exportFiles[] = fileList.listFiles();
       count_of_files=exportFiles.length;
       names_of_files = new String[exportFiles.length];
       for(int i = 0; i < names_of_files.length; i++){
           names_of_files[i] = exportFiles[i].getName();
       }
       return count_of_files;
   }//посчитали количество файлов  теперь нужно считать всю информацию их них
    //для каждого файла должен быть отдельный массив который при необходимости split

    public String[][] DataOfFiles() throws IOException {
       data = new String[count_of_files][];
       String path_of_file,line;
       int j;
       int string_count;
       for(int i=0;i<count_of_files;i++){
           j=0;
           path_of_file=path_directory+"/"+names_of_files[i];
           File f = new File(path_of_file);
           BufferedReader fin = new BufferedReader(new FileReader(f));
           string_count=getStringCount(f);
           data[i]=new String[string_count];
           while ((line = fin.readLine()) != null) {
               data[i][j] = line;
               j++;
           }
       }
       return data;
    }

    public int getStringCount(File file)
    {
        int i=0;
        String line=null;
        BufferedReader bufferedReader = null;
        try{
            FileReader fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            while(bufferedReader.readLine()!=null)
                i++;
            bufferedReader.close();
        }catch(Exception e){}
        return i;
    }

    public ArrayList<String[]> SplitEachMass(String[] mass){
       //каждый элемент ArrayList это массив из трех или четырех строчек в зависимости от файла
        ArrayList<String[]> lines=new ArrayList<String[]>();
        for(int i=2;i<mass.length;i++){
           //System.out.println(mass[i]);
            lines.add(mass[i].split("\t\t"));
        }
        return lines;
    }

    public double[] DataColumn(ArrayList<String[]> list,String parametr){
       int count_column=list.get(1).length;
       System.out.println(count_column);
       double[] data=new double[0];
       int number_of_column;
       if(count_column==3){//значит файл small
           data=new double[list.size()];
           if(parametr.contains("Resident")){
               for(int i=1;i<data.length;i++){
                   String number=list.get(i)[0];
                   if(number.contains(","))
                   {
                     number=  number.replace(",","");
                   }
                   data[i-1]=Double.parseDouble(number);
               }
           }
           if(parametr.contains("Non-Residen")){
               for(int i=1;i<data.length;i++){
                   String number=list.get(i)[1];
                   if(number.contains(","))
                   {
                      number= number.replace(",","");
                   }
                   data[i-1]=Double.parseDouble(number);
               }
           }
           if(parametr.contains("Abroad")){
               for(int i=1;i<data.length;i++){
                   String number=list.get(i)[2];
                   if(number.contains(","))
                   {
                      number= number.replace(",","");
                   }
                   data[i-1]=Double.parseDouble(number);
               }
           }

       } else if(count_column==4){//значит файл big
          data=new double [list.size()];
           if(parametr.contains("Patent")){
               for(int i=0;i<data.length;i++){
                   String number=list.get(i)[0];
                   if(number.contains("null")){
                       number="0";
                   }
                   if(number.contains(","))
                   {
                      number= number.replace(",","");
                   }
                   data[i]=Double.parseDouble(number);
               }
           }
           if(parametr.contains("Trademark")){
               for(int i=0;i<data.length;i++){
                   String number=list.get(i)[1];
                   if(number.contains("null")){
                       number="0";
                   }
                   if(number.contains(","))
                   {
                       number=number.replace(",","");
                   }
                   data[i]=Double.parseDouble(number);

               }
           }
           if(parametr.contains("IndDes")){
               for(int i=0;i<data.length;i++){
                   String number=list.get(i)[2];
                   if(number.contains("null")){
                       number="0";
                   }
                   if(number.contains(","))
                   {
                     number=  number.replace(",","");
                   }
                   data[i]=Double.parseDouble(number);

               }
           }else
           if(parametr.contains("GDP")){
               for(int i=0;i<data.length;i++){
                   String number=list.get(i)[3];
                   if(number.contains("null")){
                       number="0";
                   }
                   if(number.contains(","))
                   {
                      number= number.replace(",","");
                   }
                   data[i]=Double.parseDouble(number);
               }
           }

       }
       return data;
    }
    public double[] Calculator(double[] abroad,double[] resident,double[] nonres){
       double[] result=new double[abroad.length];
       for(int i=0;i<result.length;i++){
           result[i]=abroad[i]/(resident[i]+nonres[i]);
       }
       return result;
    }
    public double[] Calculator(double[] first,double[] second){
        double[] result=new double[first.length];
        for(int i=0;i<result.length;i++){
            result[i]=first[i]/second[i];
        }
        return result;
    }
    public double[] YearsData(double[] a,int start,int stop){
       int delta=Math.abs(stop-start + 1);//то сколько должно остаться значений
        double[] result=new double[delta];
        for(int i=0;i<delta;i++){
            //System.out.println(Change(start));
            result[i]=a[Change(start)];
            start++;
        }
        return result;
    }
    public double[] Years(int start,int stop){
        int delta=Math.abs(stop-start + 1);//то сколько должно остаться значений
        //System.out.println("Delta"+delta);
        double[] result=new double[delta];
        for(int i=0;i<delta;i++){
           // System.out.println(start);
            result[i]=start;
            start++;
        }
        return result;
    }
    public int Change(int start){
       start=start-2007;
       return start;
    }
}
