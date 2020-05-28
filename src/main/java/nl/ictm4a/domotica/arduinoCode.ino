// LIGHT SENSOR
const int ldrPin = A0;
// LEDS
const int redLed = 2; // heater led
const int greenLed = 3; // lighting led

char inputBuffer[10];   // For incoming serial data


void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600); // to see what we get
  pinMode(redLed, OUTPUT); // red is for heating
  pinMode(greenLed, OUTPUT); // green is for lighting
}

void loop() {
  Serial.println(analogRead(ldrPin));   // reads the ldr value, this is getting read by java ArduinoListener.java
  delay(1000); // delay, might have to change this to millis()

  if (Serial.available() > 0) { // reads incoming from the java ArduinoListener.java application
    Serial.readBytes(inputBuffer, Serial.available());
    int incomingInt = inputBuffer[0]-'0'; // convert it into an integer

    if(incomingInt == 1 && greenLed != HIGH) digitalWrite(greenLed, HIGH);// if '1' has been sent by java turn the green led on, its dark!
    if(incomingInt == 2 && greenLed != LOW) digitalWrite(greenLed, LOW); // if '2' has been sent by java turn the green led off, its light.
    if(incomingInt == 3 && redLed != HIGH) digitalWrite(redLed, HIGH);// if '3' has been sent by java turn the red led on, its cold!
    if(incomingInt == 4 && redLed != LOW) digitalWrite(redLed, LOW); // if '4' has been sent by java turn the red led off, its warm.
    
    Serial.flush(); // i really dont know, flush it all to reset it?
  }
  
}
