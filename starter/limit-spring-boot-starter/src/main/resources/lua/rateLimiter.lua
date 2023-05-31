local key = KEYS[1]

local limit = tonumber(ARGV[1])

local expire = tonumber(ARGV[2])

local exists = redis.call("EXISTS", key)

if exists == 0 then -- 如果 key 不存在
    redis.call("INCRBY", key, 1) -- 请求数 + 1
    redis.call("EXPIRE", key, expire) -- 设置过期时间
else
    local currentLimit = tonumber(redis.call("get", key) or "0")
    if currentLimit + 1 > limit then -- 请求上限
        return false
    else
        redis.call("INCRBY", key, 1) -- 请求数 + 1
    end
end
return true