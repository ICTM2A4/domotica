import _thread
import queue
from sense_hat import SenseHat

import time
import pygame
import socket
from socket import error as SocketError

sense = SenseHat()

file = 'file1.mp3'
pygame.init()
pygame.mixer.init()
pygame.mixer.music.load(file)
pygame.mixer.music.play(-1) # If the loops is -1 then the music will repeat indefinitely.
pygame.mixer.music.pause()

soc = socket.socket()
host = "192.168.1.14"
port = 8001
soc.bind((host, port))
soc.listen(5)
connection = ""
command = ""
source = ""
message = ""
paused = True

def togglePause():
	global paused
	if paused == False:
		print('pauze')
		pygame.mixer.music.pause()
		paused = True
	else:
		print('doorgaan')
		pygame.mixer.music.unpause()
		paused = False

def nextSong():
	print('volgende')
	pygame.mixer.music.load(command[1])
	pygame.mixer.music.play()
	paused = False
	
def previousSong():
	print('vorige')
	pygame.mixer.music.load(command[1])
	pygame.mixer.music.play()
	paused = False
	
def playMusic():
	#volgende vorige toggled pauze niet goed
	
	global command

	while pygame.mixer.music.get_busy(): 
		if command != "":
			command = command.split(',')
			if command[0] == "pause":
				togglePause()
			elif command[0] == "next":
				nextSong()
			elif command[0] == "previous":
				previousSong()
			command = ""
		pygame.time.Clock().tick(10)
		
def lookForConnection():
	print("waiting for connection")
	while True:
		try:
			global connection
			conn, addr = soc.accept()
			print("Got connection from",addr)
			connection = conn
			print(connection)
		except SocketError as e:
			if e.errno != errno.ECONNRESET:
				raise
			print('Client disconnected')
			pass
		break

	
def checkForInput(connection):
	global command
	while True:
		try:
			command = (connection.recv(2048).decode('utf-8')[2:])
		except SocketError as e:
			if e.errno != errno.ECONNRESET:
				raise
			print('Client disconnected')
			pass

def sendMessage( value, conn ):
    message_to_send = value.encode("UTF-8")
    conn.send(len(message_to_send).to_bytes(2, byteorder='big'))
    conn.send(message_to_send)

def checkForPiInput(connection):
	global command
	while True:
		for event in sense.stick.get_events():
			if event.action == "released":
				if event.direction == "right":
					command = "previous"
					sendMessage("dummy", connection)
					sendMessage(command, connection)
				if event.direction == "middle":
					command = "pause"
					if paused:
						sendMessage("dummy", connection)
						sendMessage("play", connection)
					else:
						sendMessage("dummy", connection)
						sendMessage(command, connection)
				if event.direction == "left":
					command = "next"
					sendMessage("dummy", connection)
					sendMessage(command, connection)
				
lookForConnection()
if(connection != ""):
	try:
		_thread.start_new_thread(playMusic, ())
		_thread.start_new_thread(checkForInput, (connection,))
		_thread.start_new_thread(checkForPiInput, (connection,))
		
	except:
		print("kan thread niet starten")
		pass
	
while True:
	pass

