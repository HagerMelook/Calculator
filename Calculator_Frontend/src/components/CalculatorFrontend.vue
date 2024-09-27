<template>
    <div class="container-fluid">
        <div class="jumbotton col-sm-4 p-2 m-0 bg-inverse">
            <h1 class="text-center">Calculator</h1>
        
        <!--Display the expression-->
            <div class="input-group input-group-sm col-sm-12 m-0 p-0">
                <div class="form-control text-lg-right" type="text">{{expression}}</div>
            </div>
            <!--Display the appear-->
            <div class="input-group input-group-sm col-sm-12 m-0 p-0">
                <div class="form-control text-lg-right" type="text">{{appear}}</div>
            </div>
            <div class="col-sm-12 p-1 m-0">
                <button class="btn btn-secondary col-sm-3" type="button" @click="pressUniOperator('^')">%</button><!-- i use ^ to avoid miss interpolation of %-->
                <button class=" btn btn-warning col-sm-3" type="button" @click="allclear()">CE</button>
                <button class="btn btn-warning col-sm-3" type="button" @click="allclear()">C</button>
                <button class="btn btn-warning col-sm-3" type="button" @click="clear()">X</button>
            </div>
            <div class="col-sm-12 p-1 m-0">
                <button class="btn btn-secondary col-sm-3" type="button" @click="pressUniOperator('$')">1/x</button><!-- i use ^ to avoid miss interpolation of 1/x-->
                <button class="btn btn-secondary col-sm-3" type="button" @click="pressUniOperator('x^2')">x^2</button>
                <button class="btn btn-secondary col-sm-3" type="button" @click="pressUniOperator('sqr')">sqr</button>
                <button class="btn btn-secondary col-sm-3" type="button" @click="pressBinOperator('÷')">÷</button>
            </div>
            <div class="col-sm-12 p-1 m-0">
                <button class="btn btn-lg btn-outline-primary col-sm-3 p-1" type="button" @click="pressNum('7')">7</button>
                <button class="btn btn-lg btn-outline-primary col-sm-3 p-1" type="button" @click="pressNum('8')">8</button>
                <button class="btn btn-lg btn-outline-primary col-sm-3 p-1" type="button" @click="pressNum('9')">9</button>
                <button class="btn btn-secondary col-sm-3" type="button" @click="pressBinOperator('*')">*</button>
            </div>
            <div class="col-sm-12 p-1 m-0">
                <button class="btn btn-lg btn-outline-primary col-sm-3 p-1" type="button" @click="pressNum('4')">4</button>
                <button class="btn btn-lg btn-outline-primary col-sm-3 p-1" type="button" @click="pressNum('5')">5</button>
                <button class="btn btn-lg btn-outline-primary col-sm-3 p-1" type="button" @click="pressNum('6')">6</button>
                <button class="btn btn-secondary col-sm-3" type="button" @click="pressBinOperator('-')">-</button>
            </div>
            <div class="col-sm-12 p-1 m-0">
                <button class="btn btn-lg btn-outline-primary col-sm-3 p-1" type="button" @click="pressNum('1')">1</button>
                <button class="btn btn-lg btn-outline-primary col-sm-3 p-1" type="button" @click="pressNum('2')">2</button>
                <button class="btn btn-lg btn-outline-primary col-sm-3 p-1" type="button" @click="pressNum('3')">3</button>
                <button class="btn btn-secondary col-sm-3" type="button" @click="pressBinOperator('+')">+</button>
            </div>
            <div class="col-sm-12 p-1 m-0">
                <button class="btn btn-lg btn-outline-primary col-sm-3 p-1" type="button" @click="pressUniOperator('++')">+/-</button><!-- i use ^ to avoid miss interpolation of +/--->
                <button class="btn btn-lg btn-outline-primary col-sm-3 p-1" type="button" @click="pressNum('0')">0</button>
                <button class="btn btn-lg btn-outline-primary col-sm-3 p-1" type="button" @click="pressNum('.')">.</button>
                <button class=" btn btn-info col-sm-3" type="button" @click="getResult()">=</button>
            </div>
        </div>
    </div>
</template>
<script>
    export default{
            data() {
            return {
                firstOperand: "",
                secondOperand: "",
                appear: "",
                operator: "",
                expression: "",
                operand: "",
                operatorClicked: false,
                firstOperator: false,
                equality: false
                };
            },
            methods: {
            clear() {
                this.appear = this.appear.substring(0,this.appear.length-1);
            },
            allclear() {
                this.appear = "";
                this.expression = "";
            },
            pressNum(number) {
                if(this.equality){
                    this.expression ="";
                    this.firstOperator=false;
                    this.equality = false
                    this.appear = "";
                }
                if (this.operatorClicked || this.equality) {
                    this.firstOperand=this.appear;//
                    if(!this.firstOperator){
                        this.expression = `${this.firstOperand}${this.operator}`;
                    }
                    this.appear = "";
                    this.operatorClicked = false;
                }
                this.appear = `${this.appear}${number}`;
            },
            pressUniOperator(operator) {
                if (this.appear !== "") {
                    fetch("http://localhost:8080/" + this.appear + "/" + operator, {method: "get"})
                .then(response => {
                return response.text();
                })
                .then(data => {
                switch(operator){
                    case "^": this.expression = `${this.appear}${"%"}`
                                break;
                    case "++": this.expression = `${"(+/-)"}${this.appear}`
                                break;
                    case "sqr": this.expression = `${"sqr("}${this.appear}${")"}`
                                break;
                    case "$": this.expression = `${"1/"}${this.appear}`
                                break;
                    case "x^2": this.expression = `${this.appear}${"^2"}`
                                break;
                }
                this.appear = data;
                });
                }
            },
            pressBinOperator(operator){
                if (!this.operatorClicked && this.appear !== ""&& !this.firstOperator) {
                    this.firstOperand = this.appear;
                    this.expression = `${this.firstOperand}${operator}`;//
                    this.operatorClicked = true;
                    this.firstOperator = true;
                    this.equality = false;
                    }
                    else if (this.firstOperator && this.appear !== ""){
                    this.ExcuteBinOperator(this.operator);
                    this.operatorClicked = true;
                    }
                    this.operator = operator;
            },
            ExcuteBinOperator(operator){
                if (!this.equality  &&  this.appear !== "" ) {
                    this.secondOperand = this.appear;
                fetch("http://localhost:8080/" +this.firstOperand + "/" + operator +"/" + this.secondOperand,{ method: "get" })
                .then(response => {
                return response.text();
                })
                .then(data => {
                this.appear = data;
                this.expression =`${this.appear}`
                if(!this.equality)
                this.expression =`${this.expression}${this.operator}`
                });
            }},
            getResult() {
                this.ExcuteBinOperator(this.operator)
                if (!this.equality  &&  this.appear !== "" ) {
                    this.firstOperand = this.appear;
                    this.firstOperator = false;
                    this.equality = true;
                    this.operatorClicked = false;
                }
            }
        }
    }
</script>