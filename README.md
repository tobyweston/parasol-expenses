# Parasol Expense Scrapper

Scrap a financial year's worth of expenses and attempt to categorise them.

## Background

In 

Parasol sent emails out and publish on their website ([http://www.parasolgroup.co.uk/help-me-decide/guides/expenses/](http://www.parasolgroup.co.uk/help-me-decide/guides/expenses/)):

> "Parasol will help you with this by providing you with all the information HMRC will need, so long as you regularly log all of your expenses and upload receipts on the MyParasol portal so we can track and check them."

Naively, I assumed Parasol would be able to provide a report that I could use come self assessment time.

When the time came around, I enquired with Parasol:

> "I'm due to prepare my self assessment so wanted to ask if you could help with regards to expenses. Can you outline how it works and how you're able to help please?"

... to which they replied:

> Dear Toby,﻿
>  ﻿
> Thank you for your email.﻿
>   ﻿
> All the expenses that you have entered are available for you to view on the Parasol portal. 
>﻿
> For advise on how to complete the self assessment tax return I would advise you to contact HMRC. 
>
> Parasol

After some more enquiry, it became clear that Parasol were going to offer zero assistance in gathering my expense data.

> To be clear though I wasn't asking for help with completing my self assessment but with information regarding expenses and the recent changes. Your site (http://www.parasolgroup.co.uk/help-me-decide/guides/expenses/) states
>
> "Parasol will help you with this by providing you with all the information HMRC will need, so long as you regularly log all of your expenses and upload receipts on the MyParasol portal so we can track and check them."
> 
> So can you provide me with the expense information in an appropriate form? The 'All Claims' area of the portal shows all my expenses but not in a single report and there doesn't seem to be a way for me to download a report or summary. Similarly, won't I have to differentiate between expenses that you've already paid me and those that I will need to supply to HMRC (i.e. you pay some directly to me)? Can you supply this?   
>
> Toby

Parasol sent me this reply.

> Thank you for your email.
>  ﻿
> The assistance that we provide in regards to this is all available on our portal. We display the expenses that have been submitted to us throughout the year and these would need to be transitioned across to the Self assessment for HMRC.
>
> We would not provide a document detailing the expenses and differentiate between the ones that have been reimbursed and the ones that have not. The portal will detail all expenses and also show any receipts that have been entered. 
>
> Apologies for any inconvenience caused in regards to this.
  
So that would be zero assistance then. I have to click through for every week's worth of expenses, click again to see the receipts, download each (one click each), go back to the screen I was just on and manually transpose the amounts and dates. 

Fuck that. I wrote a scrapper.﻿ 

# Usage

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

Go get 'em Tiger.