local convert_queue_key = KEYS[1]

local convert_ticket_num = tonumber(ARGV[1])
local convert_ticket_name = string.gsub(ARGV[2],"\"","")
local uuid = string.gsub(ARGV[3],"\"","")

redis.call("SELECT", 3)
redis.call("DEL",convert_queue_key)

for x=1,convert_ticket_num,1 do
    local convert_ticket_newName = convert_ticket_name.."_"..x
    redis.call("RPUSH",convert_queue_key,"\""..convert_ticket_newName.."_"..uuid.."\"")
    redis.call("SET",convert_ticket_newName,"\""..uuid.."\"")
    --redis.call("EXPIRE",convert_ticket_newName,-1)
end
