local key = KEYS[1]

redis.call("SELECT", 4)

local file_info = redis.call("GET",key)

return file_info