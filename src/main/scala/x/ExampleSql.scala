package x

object ExampleSql {

  val rtts =
    """select timeDiff(resp.at,req.at) as rtt, req.mti, req.stan
from logs req, logs resp
where req.mti is not null and resp.mti is not null
and isResponseMti(resp.mti)
and resp.mti = invertMti(req.mti)
and resp.stan = req.stan
and resp.realm like 'terminal%' and req.realm like 'terminal%'"""
}
