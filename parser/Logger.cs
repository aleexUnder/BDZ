using System;
using System.IO;

namespace parser
{
    public static class Logger
    {

        public static void Info(string s)
        {
            try
            {
                string path = "log.txt";

                //Pass the filepath and filename to the StreamWriter Constructor
                StreamWriter sw = new StreamWriter(Path.Combine(Environment.GetFolderPath(
                    Environment.SpecialFolder.Desktop), path.Trim()), true, System.Text.Encoding.Default);

                //Write a line of text
                sw.WriteLine(DateTime.Now.ToString("dd.MM.yyyy hh:mm:ss:fff") + " " + s);


                //Close the file
                sw.Close();
            }
            catch (Exception e)
            {
                Console.WriteLine("Exception: " + e.Message);
            }

        }
    }
}
