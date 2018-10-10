using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.IO;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace parser
{
    class Program
    {
        static void Main(string[] args)
        {
            int times = 0;
            int p = 0;
            try
            {
                if (args != null)
                {
                    times = Convert.ToInt32(args[0]);
                    p = 0;
                }
            }
            catch
            {
                times = 100;
            }

            string pathFolder = "HTML";

            string[] dirs = Directory.GetFiles(Path.Combine(Environment.GetFolderPath(
                    Environment.SpecialFolder.Desktop), pathFolder), "*.html");
            //Console.WriteLine("The number of files starting with html is {0}.", dirs.Length);
            foreach (string dir in dirs)
            {
                try
                {
                    if (args != null)
                    {
                        p++;
                        if (p > times)
                        {
                            break;
                        }
                    }
                }
                catch
                {

                }
                Logger.Info("Начало работы");

                Regex pattern = new Regex(@"\w*text-align:right;line-height:normal'><span style='font-size:12.0pt'>\w*");
                Regex pattern2 = new Regex(@"\w*nbsp\w*");
                Regex pattern3 = new Regex(@"\b\d{3,}\b");
                Regex pattern4 = new Regex(@"^\d|^&");
                Regex pattern5 = new Regex(@"\w*font-size:24.0pt\w*");

                //string path1 = "HTML\\ar.html";
                //string path2 = "ar.txt";


                Logger.Info("Парсинг страницы:" + dir);
                Logger.Info("Запись результата в:" + dir);
                string line = "";
                string res = "";
                string res2 = "";
                string name = "";
                int d = 0;


                try
                {

                    StreamReader sr = new StreamReader(Path.Combine(Environment.GetFolderPath(
                        Environment.SpecialFolder.Desktop), dir));
                    line = sr.ReadLine();
                    int i = 0;
                    while (d < 77)
                    {
                        MatchCollection matches = pattern.Matches(line);
                        MatchCollection matches5 = pattern5.Matches(line);
                        if (matches5.Count > 0)
                        {
                            line = line.Substring(71);
                            int obr = line.Length;
                            line = line.Remove(obr - 22);
                            name = line;
                            res2 += line + "\r\n" + "Patent\t\tTrademark\tIndDes\t\tGDP\r\n";
                            res += line + "\r\n" + "Resident\tNon-Residen\tAbroadGDP\r";
                        }
                        if (matches.Count > 0)
                        {
                            line = line.Substring(72);
                            int obr = line.Length;
                            line = line.Remove(obr - 22);
                            MatchCollection matches4 = pattern4.Matches(line);
                            if (line == "&nbsp;") line = "null";
                            if (matches4.Count > 0)
                            {
                                if (d <= 43)
                                {
                                    // Console.WriteLine(line);
                                    if (i < 4)
                                    {
                                        //Console.WriteLine(line);
                                        res2 += line + "\t" + "\t";
                                        //d++;
                                        i++;
                                        Logger.Info("Запись строки");

                                    }

                                    else
                                    {
                                        //Console.WriteLine(line);
                                        res2 += "\r\n" + line + "\t" + "\t";
                                        //d++;
                                        i = 1;
                                        Logger.Info("Запись строки");
                                    }
                                }



                                if (d > 43)
                                {

                                    // Console.WriteLine(line);
                                    if (i < 3)
                                    {
                                        //Console.WriteLine(line);
                                        res += line + "\t" + "\t";
                                        //d++;
                                        i++;
                                        Logger.Info("Запись строки");
                                    }
                                    else
                                    {
                                        //Console.WriteLine(line);
                                        res += "\r\n" + line + "\t" + "\t";
                                        //d++;
                                        i = 1;
                                        Logger.Info("Запись строки");
                                    }
                                }
                                //else
                                //{
                                //    d++;
                                //}

                            }
                            d++;
                        }

                        line = sr.ReadLine();
                    }
                    Logger.Info("Конец записи");
                    sr.Close();

                }
                catch (Exception e)
                {
                    Console.WriteLine("Exception: " + e.Message);
                }

               // Console.WriteLine(res2);
                //Console.WriteLine("*********************************************");
                //Console.WriteLine(res);


                string pathDB = "HTMLdb\\Big" + name + ".txt";
                string pathDB2 = "HTMLdb\\Small" + name + ".txt";
                try
                {
                    Logger.Info("Открытие файла");
                    //Pass the filepath and filename to the StreamWriter Constructor
                    StreamWriter sw = new StreamWriter(Path.Combine(Environment.GetFolderPath(
                        Environment.SpecialFolder.Desktop), pathDB));

                    //Write a line of text
                    sw.WriteLine(res2);
                    Logger.Info("Запись результатов");

                    //Close the file
                    sw.Close();
                }
                catch (Exception e)
                {
                    Console.WriteLine("Exception: " + e.Message);
                }
                finally
                {
                    Console.WriteLine("Executing finally block.");
                }

                try
                {
                    Logger.Info("Открытие файла");
                    //Pass the filepath and filename to the StreamWriter Constructor
                    StreamWriter sw = new StreamWriter(Path.Combine(Environment.GetFolderPath(
                        Environment.SpecialFolder.Desktop), pathDB2));

                    //Write a line of text
                    sw.WriteLine(res);
                    Logger.Info("Запись результатов");

                    //Close the file
                    sw.Close();
                }
                catch (Exception e)
                {
                    Console.WriteLine("Exception: " + e.Message);
                }
                finally
                {
                    Console.WriteLine("Executing finally block.");
                }

                Logger.Info("Завершение работы парсера");



            }



        }
    }
}
