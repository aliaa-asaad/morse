#include <SoftwareSerial.h>
#define irSensor 7
String code = "";
String oneCode="";
unsigned long dot,dash;  
SoftwareSerial bluetooth(2,3);

//dot & dash value
String Blink(){
  if (digitalRead(irSensor) == LOW){
    dot=millis();
    if(dot<600){
    return".";}}
    
  if (digitalRead(irSensor) == HIGH){
    dash=millis();
    if(dash>600 && dash<6000 ){
    return "-";}}
    
    return "";
   }
  


String lettersCode[] = {".-", "-...", "-.-.", "-..", ".","..-.","....." "--.", "....", "..", 
                           ".---", "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.",
                           "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--..","......"
                           };
                           
String letters[] ={"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t",
                    "u","v","w","x","y","z"," ","I feel pain,I want to take my medicine",
                   "I am hungry","I am thirsty","I need help","I feel hot"};
  
void setup() {
  pinMode(irSensor,INPUT);
  Serial.begin(9600);
  bluetooth.begin(9600);
  // put your setup code here, to run once:

}

void loop() 
{
  
  if (bluetooth.available()>0){
 Serial.println(bluetooth.read());
 }
 
 if(Serial.available()){
    bluetooth.write(Serial.read());
  }
  int i=0;
  while(digitalRead(irSensor)){
    
    code=Blink();
    oneCode+=code;
    i++;
    }
    for (int x=0; x<=32;x++){
    if ( oneCode == lettersCode[x])
      { 
        
        Serial.print(letters[x]);
        bluetooth.write(letters[x]);
        break;
      }
      }
      
  
  
}
