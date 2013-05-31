#!/usr/bin/ruby

require 'optparse'
require 'rubygems'
require 'fileutils'
require 'socket'      # Sockets are in standard library

current_path = Dir.pwd

if(!File.exists?("target/dvms.jar"))
	system("sbt assembly")
end



for port in 3000..3005
	if(port == 3000)
   		system("java -jar target/dvms.jar ip=127.0.0.1 port=#{port} debug=true &")
        sleep(2)
	else
   		system("java -jar target/dvms.jar ip=127.0.0.1 port=#{port} remote_ip=127.0.0.1 remote_port=3000 debug=true &")
	end
end


puts "End of the program"
#raise "Killing the remaining threads"
