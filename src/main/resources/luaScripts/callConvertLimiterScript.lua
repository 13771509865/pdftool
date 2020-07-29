local key = KEYS[1] --限流KEY（一秒一个）
local limit = tonumber(ARGV[1])        --限流大小
local time = tonumber(ARGV[2])        --过期时间

local current = tonumber(redis.call("INCRBY", key, "1")) --请求数+1

if current > limit then --如果超出限流大小
   return 0
elseif current == 1 then  --只有第一次访问需要设置2秒的过期时间
   redis.call("expire", key,time)
end
return 1