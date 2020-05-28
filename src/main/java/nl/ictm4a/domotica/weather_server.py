import socket
from socket import error as SocketError
from sense_hat import SenseHat
import time
import errno

sense = SenseHat()
soc = socket.socket()
host = "192.168.1.14"
port = 8000
soc.bind((host, port))
soc.listen(5)

def sendMessage( value, conn ):
    message_to_send = value.encode("UTF-8")
    conn.send(len(message_to_send).to_bytes(2, byteorder='big'))
    conn.send(message_to_send)
    
while True:
    try:
        conn, addr = soc.accept()
        print("Got connection from",addr)
        
        while True:
            sendMessage(str(sense.get_pressure()) + ", " + str(sense.get_temperature()) + ", " + str(sense.get_humidity()), conn)
            time.sleep(5)
    except SocketError as e:
        if e.errno != errno.ECONNRESET:
            raise
        print('Client disconnected')
        pass
        
        
  
