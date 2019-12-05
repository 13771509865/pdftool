local low_queue_key = KEYS[1]
local middle_queue_key = KEYS[2]
local high_queue_key = KEYS[3]
local low_ticket_num = tonumber(ARGV[1])
local middle_ticket_num = tonumber(ARGV[2])
local high_ticket_num = tonumber(ARGV[3])
local low_ticket_name = string.gsub(ARGV[4],"\"","")
local middle_ticket_name = string.gsub(ARGV[5],"\"","")
local high_ticket_name = string.gsub(ARGV[6],"\"","")
local uuid = string.gsub(ARGV[7],"\"","")

redis.call("SELECT", 2)
redis.call("DEL",low_queue_key)
redis.call("DEL",middle_queue_key)
redis.call("DEL",high_queue_key)

for x=1,low_ticket_num,1 do
    local low_ticket_newName = low_ticket_name.."_"..x
    redis.call("RPUSH",low_queue_key,"\""..low_ticket_newName.."_"..uuid.."\"")
    redis.call("SET",low_ticket_newName,"\""..uuid.."\"")
    --redis.call("EXPIRE",low_ticket_newName,-1)
end

for y=1,middle_ticket_num,1 do
    local middle_ticket_newName = middle_ticket_name.."_"..y
    redis.call("RPUSH",middle_queue_key,"\""..middle_ticket_newName.."_"..uuid.."\"")
    redis.call("SET",middle_ticket_newName,"\""..uuid.."\"")
    --redis.call("EXPIRE",middle_ticket_newName,-1)
end

for z=1,high_ticket_num,1 do
    local high_ticket_newName = high_ticket_name.."_"..z
    redis.call("RPUSH",high_queue_key,"\""..high_ticket_newName.."_"..uuid.."\"")
    redis.call("SET",high_ticket_newName,"\""..uuid.."\"")
    --redis.call("EXPIRE",high_ticket_newName,-1)
end