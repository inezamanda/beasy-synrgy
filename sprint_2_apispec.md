# Gamification #

## List Planet [GET] [api/gamificatiom/planets] ##

Include : accomplished & next mission

### Request ###
```json
Header : Authorization : Bearer <Token>
Query Parameter : status [ACCOMPLISHED/NEXT] (Opsional)
```

### Response ###
```json
[
    {
        "success" : true,
        "message" : "Successfully get all planet",
        "data" : [
            {
                "id" : "dsadasdas0i32103mdsadas",
                "planet_name" : "Planet Asgard",
                "status" : "ACCOMPLISHED/CURRENT/NEXT",
                "reward_wording" : "Cashback IDR 10,000.00",
                "reward_is_claimed" : false
            }
        ]
    }
]
```

## Detail Planet [GET] [api/gamificatiom/planets/{planetId}] ##

### Request ###
```json
Header : Authorization : Bearer <Token>
```

### Response ###
```json
[
    {
        "success" : true,
        "message" : "Successfully get all planet",
        "data" : [
            {
                "id" : "dsadasdas0i32103mdsadas",
                "planet_name" : "Planet Asgard",
                "status" : "DONE/CURRENT/NEXT",
                "mission" : [
                    {
                        "id" : "adasdasdasdsadas",
                        "wording": "2x interbank transfer",
                        "status": "COMPLETED/IN_PROGRESS"
                    },
                    {
                        "id" : "adasdasdasdsadas",
                        "wording": "Do a minimum top up IDR 10,000.00 from the main account to one of the pockets",
                        "status": "COMPLETED/IN_PROGRESS"
                    }
                ],
                "reward_id" : "adadoaisdpsaidaspip3i1pipdas"
            }
        ]
    }
]
```

Note :
- status tidak diambil dr kolom tp dengan logic sprti ini : 
- cek di user relasi ke planet, dia di sequence berapa
- jika planet yg dipilih sequence sebelum planet user skrg : DONE
- jika planet yg dipilih sequence setelah planet user skrg : NEXT
- jika planet yang dipilih sequence sama dengan planet user skrg : CURRENT

## Get Planet Reward [GET] [api/gamificatiom/planets/{planetId}/reward] ##

### Request ###
```json
Header : Authorization : Bearer <Token>
```

### Response ###
```json
[
    {
        "success" : true,
        "message" : "Successfully get planet reward",
        "data" : [
            {
                "planet_id" : "dsadasdas0i32103mdsadas",
                "planet_name" : "Planet Asgard",
                "user_reward_id" : "sadasdasdasdasdas",
                "wording" : "Additonal Balance IDR 10,000.00",
                "tnc" : "Cashback will be sent to your main balance Valid 1 month after you complete the mission Your reward cannot be redeemed after 1 month",
                "is_claimed" : false,
                "expired_at" : "2021/13/03",
                "is_expired" : false
            }
        ]
    }
]
```

## Get Planet Reward [GET] [api/gamificatiom/planets/{planetId}/reward] ##

### Request ###
```json
Header : Authorization : Bearer <Token>
```

### Response ###
```json
[
    {
        "success" : true,
        "message" : "Successfully get planet reward",
        "data" : [
            {
                "planet_id" : "dsadasdas0i32103mdsadas",
                "planet_name" : "Planet Asgard",
                "user_reward_id" : "sadasdasdasdasdas",
                "wording" : "Additonal Balance IDR 10,000.00",
                "tnc" : "Cashback will be sent to your main balance Valid 1 month after you complete the mission Your reward cannot be redeemed after 1 month",
                "is_claimed" : false,
                "expired_at" : "2021/13/03",
                "is_expired" : false
            }
        ]
    }
]
```

## CLAIM Reward [POST] [api/gamificatiom/reward/{rewardId}/claim] ##

### Request ###
```json
Header : Authorization : Bearer <Token>
URL Parameter : Reward Id
```

### Response ###
```json
[
    {
        "success" : true,
        "message" : "Successfully claim planet reward",
        "data" : [
            {
                "user_reward_id" : "sadasdasdasdasdas",
                "is_claimed" : true,
            }
        ]
    }
]
```

Note :
- rewardId ngambil dr tabel reward
- user_reward_id : ngambil dr tabel user_reward