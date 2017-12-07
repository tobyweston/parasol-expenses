# Parasol Expense Scrapper

Scrap a financial year's worth of expenses and attempt to categorise them.

## Credentials

Set some VM options for your credentials to http://myparasol.co.uk

```
-Duser=bobbuilder -Dpassword=secretpassphrase
```

## Caveats

There are plenty. This is hacky-as-hell as I needed something fast to help get my self assessment done. You're probably going to have to hack it too if you want it to work for you.

Specifically:

* Pagination of the expenses page isn't supported. It assumes you'll have less than 50 expenses for a given tax year.
* The tax year is hardcoded. Go find it.
* Not all expense types are supported (see `Category` and `ExpenseClaimPage`), just add to the pattern matches if you need to.