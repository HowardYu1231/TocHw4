//TocHW4
//Name:HowardYu

import org.json.*;
import java.io.*;
import java.net.*;
import java.util.*;



public class TocHw4 {
		 
		 //class to save different address data 
		 public static class A_data{
			 private String Address_content;
			 private boolean[][] Month = new boolean[11][13];
			 private int max_distinct_month;
			 private int max_trade;
			 private int min_trade;
			 
			 //篶 ﹍てclass A_data
			 public A_data(String address){
				 Address_content = address;
				 max_distinct_month = 0;
				 max_trade = 0;
				 min_trade = 0;
				 
				 for(int i = 1 ; i <= 12 ; i++)
				 	for(int j = 0 ; j <= 10 ;j++){
					 Month[j][i] = false;
				 }
			 }
			 
			 public String get_Address_content(){
				 return Address_content;
			 }
			 
			 
			 //砞﹚ユ肂程程
			 public void set_trade(int value){
				 if (max_trade == 0){
					 max_trade = value;
				 }
				 else{
					 if (value > max_trade){
						 max_trade = value;
					 }
				 }
					 
				 
				 if (min_trade == 0){
					 min_trade = value;
				 }else{
					 if (value < min_trade){
						 min_trade = value;
					 }
				 }
				 
				 
			 }//end set_trade function
			 
			 public int get_max_trade(){
				 return max_trade;
			 }
			 
			 public int get_min_trade(){
				 return min_trade;
			 }
			 public void set_month(int value){
				 int year = (value/100)%10;
				 int month = value%100;
				 if(month > 0 && month <= 12)
					 if(Month[year][month] == false){
						 Month[year][month] = true;
						 max_distinct_month++;
					 }
					 
			 }//end set_month function
			 
			 public int get_max_distinct_month(){
				 return max_distinct_month;
			 }//end get_max_distinct_month function
			 
			 
		 }
	
		 public static void main(String[] argv) throws Exception,IOException,FileNotFoundException{  //throws JSONException
			 
			 ArrayList<A_data> data_list = new ArrayList<A_data>();
			 
			 try{
				
			 if(argv.length == 1)
			 {
			 //System.out.println("Copy JSON file from the URL!");
			 //System.out.println("Please wait...");
			 
			 //---
			 //the following code between (//---) referenced from the website below
			 //http://robertvmp.pixnet.net/blog/post/26585200-java---%E8%AE%80%E7%B6%B2%E9%A0%81%E7%AF%84%E4%BE%8B-
			 URL pageUrl = new URL(argv[0]);
			 BufferedReader buffer_input_string = new BufferedReader(new InputStreamReader(pageUrl.openStream(), "UTF-8"));
			 BufferedWriter buffer_output_string = new BufferedWriter(new FileWriter("JSON_HW4.json", false));
			 String oneLine = null ;
			 while ((oneLine = buffer_input_string.readLine()) != null) {
				 	buffer_output_string.write(oneLine);                
	            }
			 buffer_output_string.close();
			 buffer_input_string.close(); 
			 //---
			 
			 JSONArray obj = new JSONArray(new JSONTokener(new FileReader(new File("JSON_HW4.json"))));  
			 
			 boolean conti = true ;
			 boolean write_address = false;
			 int index = 0; //array index
			 String road_to_compare;
			 int trade_money = 0 , trade_month = 0; 
			 int cut_value_road = 0 , cut_value_alley = 0 , cut_value_street = 0;
			 int index_array_list = 0;
			 
			 while (conti == true)
			 {
				 try{
					 String address = null;
					 JSONObject ob = obj.getJSONObject(index);
					 index++;
					 
					 //System.out.println(data_list.size());
					 //System.out.printf("%d \n" , data_list.size());
					 
					 road_to_compare = ob.getString("跋琿竚┪跋礟");
					 cut_value_road = road_to_compare.indexOf("隔");
					 cut_value_alley = road_to_compare.indexOf("");
					 cut_value_street = road_to_compare.indexOf("刁");
					 
					 
					 
					 if (cut_value_road == -1 && cut_value_alley == -1 && cut_value_street == -1)
						 continue;
					 
					 trade_money = ob.getInt("羆基じ");
					 trade_month = ob.getInt("ユる");
					 
					 if(cut_value_alley != -1 && cut_value_street == -1 && cut_value_road == -1)//⊿刁⊿隔Τ
					 {
						 address = road_to_compare.substring(0 , cut_value_alley+1);
						 //秨﹍Arry list⊿狥﹁
						 if(data_list.size() == 0)
						 {
							 write_address = true;
							 index_array_list = 0;
						 }
						 else{
						 for(int i = 0 ; i < data_list.size() ; i++){
							 //ぃ单=⊿瞷筁 arraylistい
							 if (data_list.get(i).get_Address_content().equals(address) == true){
								 index_array_list = i;
								 i = data_list.size();
								 write_address = false;
							 }
							 else{
								 write_address = true;
								 index_array_list = data_list.size();
							 }
						 
						 }//end for
						 }
					 }
					 else if(cut_value_street != -1 && cut_value_road == -1)//⊿隔Τ刁
					 {
						 address = road_to_compare.substring(0 , cut_value_street+1);
						 if(data_list.size() == 0)
						 {
							 write_address = true;
							 index_array_list = 0;
						 }
						 else{
						 for(int i = 0 ; i < data_list.size() ; i++){
							 //ぃ单=⊿瞷筁 arraylistい
							 //璶Τ瞷筁 碞ぃ糶秈arraylist
							 if (data_list.get(i).get_Address_content().equals(address) == true){
								 write_address = false;
								 index_array_list = i;
								 i = data_list.size();
							 }
							 else{
								 write_address = true;
								 index_array_list = data_list.size();
							 }
						 
						 }//end for
						 }
						 
					 }
					 else if(cut_value_road != -1)//Τ隔
					 {
						 address = road_to_compare.substring(0 , cut_value_road+1);
						 if(data_list.size() == 0)
						 {
							 write_address = true;
							 index_array_list = 0;
						 }
						 else{
						 for(int i = 0 ; i < data_list.size() ; i++){
							 //ぃ单=⊿瞷筁 arraylistい
							 if (data_list.get(i).get_Address_content().equals(address) == true){
								 index_array_list = i;
								 i = data_list.size();
								 write_address = false;
							 }
							 else{
								 write_address = true;
								 index_array_list = data_list.size();
							 }
						 
						 }//end for
						 }
					 }
					 else//ㄒ
					 {
						 write_address = false;
						 index_array_list = -1;
					 }
					 
					 if(write_address == true){
						 //arrayListt
						 //A_data a = new A_data(address);
						 data_list.add(new A_data(address));
						 //System.out.printf("%d-- \t" , data_list.size());
						 //System.out.printf("%d \n" , index_array_list);
						 
						 if(data_list.size() == 1){
							 data_list.get(index_array_list).set_trade(trade_money);
							 data_list.get(index_array_list).set_month(trade_month);
						 }
						 else if (index_array_list != -1){
							 data_list.get(index_array_list).set_trade(trade_money);
							 data_list.get(index_array_list).set_month(trade_month);
						 }
					 }
					 else{
						 if (index_array_list != -1){
							 data_list.get(index_array_list).set_trade(trade_money);
							 data_list.get(index_array_list).set_month(trade_month);
						 }
					 }
					 
					 index_array_list = -1;
					 write_address = false;
					 //
					 
				 }//try
				 catch(JSONException e)
				 	{
					 conti = false;
				 	}//catch
			 }//end while
			 
			 int max_distinct_month = 0;
			 
			 //System.out.print(data_list.get(index).get_Address_content());
			 
			 for (int i = 0 ; i<data_list.size() ;i++){
				 if(data_list.get(i).get_max_distinct_month() > max_distinct_month){
					 max_distinct_month = data_list.get(i).get_max_distinct_month();
				 }//enf if
			 }//end for
			 
			 for (int i = 0 ; i<data_list.size() ;i++){
				 if(data_list.get(i).get_max_distinct_month() == max_distinct_month){
				 //if(data_list.get(i).get_Address_content().indexOf("┚玭隔") != -1){
					 //System.out.printf("%d \t" , i);
					 System.out.print(data_list.get(i).get_Address_content());
					 System.out.printf(", 程蔼Θユ基: "+"%d, " + "程Θユ基: "+"%d" ,data_list.get(i).get_max_trade(), data_list.get(i).get_min_trade());
					 //System.out.printf("\t%d \t" , data_list.get(i).get_max_distinct_month());
					 System.out.println("");
				 }//enf if
			 }//end for
			 
			 }//end if
			 }//try
			 catch(IOException e)
				{
					System.out.println("File Not Found!");
				}//catch
		}//main 
}//class TocHw4