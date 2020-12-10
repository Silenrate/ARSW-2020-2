var bigInt = require("big-integer");
var memo = {};
module.exports = async function (context, req) {
    context.log('JavaScript HTTP trigger function processed a request.');

    let nth = req.body.nth
    let nth_1 = bigInt.one;
    let nth_2 = bigInt.zero;
    let answer = bigInt.zero;

    memo[0] = bigInt.zero;
    memo[1] = bigInt.one;

    function fib(n) {
        var a = bigInt.zero
        var b = bigInt.zero
        n = Math.abs(n);
    
        // If we already calculated the value, just use the same
        if(memo[n] !== undefined)
            return memo[n];
    
        // Else we will calculate it and store it and also return it
        else{
           a=fib(n-1)
           b =fib(n-2)
           memo[n] = a.add(b)
           return memo[n];
        }
    }
    answer = fib(nth);

    context.res = {
        body: answer.toString()
    };
}