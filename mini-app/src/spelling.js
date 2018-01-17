/**
 * Created by jpc on 1/14/15.
 */
/*global jQuery*/

function openChanged(matchIndex, direction, check){
    "use strict";
    if (matchIndex === 1){
        if (direction === "l"){
            value.one.openLeft = check;
            if (value.one.openRight && check){
                value.one.openRight = !check;
            }
        } else{
            value.one.openRight = check;
            if (value.one.openLeft && check){
                value.one.openLeft = !check;
            }
        }
    } else{
        if (direction === "l"){
            value.two.openLeft = check;
            if (value.two.openRight && check){
                value.two.openRight = !check;
            }
        } else{
            value.two.openRight = check;
            if (value.two.openLeft && check){
                value.two.openLeft = !check;
            }
        }
    }
    adjust();
    updateDisplay();
}

function sizeChanged(matchIndex, newSize){
    "use strict";
    var match = matchIndex === 1 ? value.one.matches : value.two.matches;
    var length = match.length;
    var i;
    if (newSize > length){
        for (i = 0; i < newSize - length; i++) {
            match.push([0, 0]);
        }
    } else if (newSize < length) {
        for (i = 0; i < length - newSize; i++) {
            match.pop();
        }
    }
    adjust();
    updateDisplay();
}

function shiftChanged(direction){
    "use strict";
    if (direction === "l"){
        value.shiftPosition = value.shiftPosition + 1;
    } else{
        value.shiftPosition = value.shiftPosition - 1;
    }
    adjust();
    updateDisplay();
}

function adjust(){
    "use strict";
    var size1 = value.one.matches.length, size2 = value.two.matches.length, comps = value.comps;
    value.shiftPosition = (size1 === 0 || size2 === 0) ? 0 : Math.min(size1 - 1, Math.max(1 - size2, value.shiftPosition));
    var size = value.shiftPosition > 0 ? Math.max(size2 + value.shiftPosition, size1) : Math.max(size1 - value.shiftPosition, size2);
    var length = comps.length, i;
    for (i = 0; i < Math.abs(size - length); i++) {
        if (size > length) {
            comps.push(0);
        } else {
            comps.pop();
        }
    }
    var i1, i2;
    for (i = 0; i < size; i++){
        i1 = (value.shiftPosition >= 0 && i < size1) || (i >= -value.shiftPosition && i < size1 - value.shiftPosition) ? (value.shiftPosition >= 0 ? i : i + value.shiftPosition) : -1;
        i2 = (value.shiftPosition <= 0 && i < size2) || (i >= value.shiftPosition && i < size2 + value.shiftPosition) ? (value.shiftPosition >= 0 ? i - value.shiftPosition : i) : -1;
        if (i1 < 0 || i2 < 0) {
            comps[i] = 0;
        }
    }
    console.log("1.openLeft", value.one.openLeft, "1.openRight", value.one.openRight, "2.openLeft", value.two.openLeft, "2.openRight", value.two.openRight);
    if (!(isCompLeftPossible() || isCompRightPossible())){
        value.compOpen = 0;
    }
}

function isCompLeftPossible(){
    return isAlignedLeft() && value.one.openLeft && value.two.openLeft;
}

function  isCompRightPossible(){
    return isAlignedRight() && value.one.openRight && value.two.openRight;
}

function isAlignedLeft(){
    "use strict";
    return (value.shiftPosition === 0 && value.one.openLeft && value.two.openLeft) || value.one.matches.length === 0 || value.two.matches.length === 0;
}

function isAlignedRight(){
    "use strict";
    return (value.one.matches.length === value.shiftPosition + value.two.matches.length && value.one.openRight && value.two.openRight) ||  value.one.matches.length === 0 || value.two.matches.length === 0;
}

function updateValue(){
    "use strict";
    var s = JSON.stringify(value);
    jQuery("#advancedSpelling").val(s);
}

var MATCH = ["?", "=", "_", "a-z", "0-9", "%-$", "a-u", "b-z", "╳", "#"];
var EXPLANATION={
    '':['Any character (default)','Specify character','Space','Letter','Digit','Special character','Vowel','Consonant','Remove this character','Identical to character #'],
    'de':['Beliebiger Buchstabe (Standardeinstellung)','Buchstabe spezifizieren','Leerzeichen','Buchstabe','Zahl','Sonderzeichen','Vokal','Konsonant','Buchstabe löschen','Identisch mit Buchstabe #'],
    'es':['Cualquier carácter (por defecto)','Especificar carácter','Espacio','Letra','Cifra','Carácter especial','Vocal','Consonante','Eliminar este carácter','Idéntico al carácter #'],
    'fr':['N\'importe quel caractère (défaut)','Caractère spécifique','Espace','Lettre','Chiffre','Caractère spécial','Voyelle','Consonne','Supprimer ce caractère','Identique au caractère #'],
    'it':['Qualsiasi carattere (default)','Specifica il carattere','Spazio','Lettera','Cifra','Carattere speciale','Vocale','Consonante','Rimuovi questo carattere','Identico al carattere #'],
    'ja':['任意の文字（デフォルト）','文字を特定する','スペース','アルファベットLetter','数','特殊な文字','母音','子音','この文字を削除してください',' 文字'],
    'ko':['임의 문자 (기본설정)','문자를 자세히 제시하시오','공간','문자/ 편지','숫자','특별 기호','모음','자음','이 특성을 삭제하기','숫자 #와 동일한'],
    'pt':['Qualquer caracter (défaut)','Caractere específico','Espaço','Letra','Cifra','Caractere especial','Vogal','Consoante','Apagar este caractere','Idêntico ao caracter '],
    'tr':['Herhangi bir karakter (varsayılan)','Karakter belirle','Boşluk','Harf','Rakam','Özel karakter','Sesli harf','Sessiz harf','Bu karakteri sil','# karakteri ile aynı'],
    'zh':['任何字符 (缺省)','指定字符','空格','字母','数字','特殊字符','元音字母','辅音字母','删除该字符','与X号字母相同']
};


function createMatch(parentCell, matchIndex, index){
    "use strict";
    parentCell.style.width = "3.5em";
    while (parentCell.firstChild) {
        parentCell.removeChild(parentCell.firstChild);
    }
    var matches = matchIndex === 1 ? value.one.matches : value.two.matches;
    if (index >= 0){
        // down arrow
        var downCell = document.createElement("span"), tn = document.createTextNode("▼");
        downCell.appendChild(tn);
        downCell.onclick = function(event){
            var r = parentCell.getBoundingClientRect();
            var popup = jQuery("#popup");
            popup.css('left', (r.left + window.pageXOffset) + "px").css('top', (r.bottom + window.pageYOffset) + "px").css("display", "block");
            popup.data("match", {
                parent: parentCell,
                matchIndex: matchIndex,
                index: index
            });
            event.stopPropagation();
        };
        parentCell.appendChild(downCell);
        // input box
        var letter = index >= 0 && matches[index][0] === 1;
        var position = index >=0 && matches[index][0] === 9;
        var valCell = document.createElement("input");
        parentCell.appendChild(valCell);
        valCell.onblur = function(){
            var s = valCell.value;
            var ms = matchIndex === 1 ? value.one.matches : value.two.matches;
            if (letter){
                ms[index][1] = s.length > 1 ? s.charCodeAt(1) : 32;
            } else if (position) {
                var n = parseInt(s.substring(1));
                ms[index][1] = isNaN(n) ? (index + 1) : n;
            }
            updateValue();
        };
        valCell.onkeyup = function(){
            if (letter){
                if (valCell.value === null || valCell.value.length !== 2 || valCell.value.charAt(0) !== '=') {
                    valCell.value = "=";
                }
            }
            if (position){
                if (valCell.value === null || valCell.value.length < 1 || valCell.value.charAt(0) !== '#'){
                    valCell.value = "#";
                } else {
                    var n = parseInt(valCell.value.substr(1));
                    if (isNaN(n) || n < 1  || n > matches.length) {
                        valCell.value = "#";
                    }
                }
            }
        };
        if (letter){
            valCell.value = "=" + (matches[index][1] < 32 ? "" : String.fromCharCode(matches[index][1]));

        } else if (position) {
            valCell.value = "#" + matches[index][1];
        } else {
            valCell.value = MATCH[matches[index][0]];
        }
        valCell.disabled = !(letter || position);
    }
    //parentCell.style.border = index >= 0 ? "1px dotted black" : "none";
}

var COMP = ["□", "=", "≠"];

function createComp(parentCell, index, clickable){
    "use strict";
    while (parentCell.firstChild) {
        parentCell.removeChild(parentCell.firstChild);
    }
    parentCell.style.width = "3em";
    var childCell = document.createElement("span");
    parentCell.appendChild(childCell);
    childCell.style.display = "inline-block";
    childCell.style.width = "2em";
    childCell.style.cursor = "pointer";
    childCell.style.textAlign = "center";
    childCell.style.fontSize = "1.5em";
    parentCell.style.removeProperty("border");
    if (clickable){
        //parentCell.style.border = "1px dotted black";
        childCell.onclick = function(){
            if (index >= 0){
                value.comps[index] = (value.comps[index] + 1) % COMP.length;
            } else{
                value.compOpen = (value.compOpen + 1) % COMP.length;
            }
            createComp(parentCell, index, clickable);
            updateValue();
        };
        childCell.textContent = index >= 0 ? COMP[value.comps[index]] : COMP[value.compOpen];
    }
}

function label(right, length, index){
    "use strict";
    if (right){
        var rev = length - index - 1;
        return rev === 0 ? "n" : "n-" + rev;
    }
    return index + 1;
}

function updateDisplay(){
    "use strict";
    updateValue();
    jQuery("#l1").prop("checked", value.one.openLeft);
    jQuery("#r1").prop("checked", value.one.openRight);
    jQuery("#l2").prop("checked", value.two.openLeft);
    jQuery("#r2").prop("checked", value.two.openRight);
    var size1 = value.one.matches.length, size2 = value.two.matches.length;
    jQuery("#cc1").val(size1);
    jQuery("#cc2").val(size2);
    var tSpelling = document.getElementById("tSpelling");
    // adjust table size to 3 + comps.size
    var comps = value.comps, shiftPosition = value.shiftPosition, delta = tSpelling.rows[0].cells.length - 3 - comps.length, i, row;
    for (i = 0; i < Math.abs(delta); i++) {
        for (row = 0; row < tSpelling.rows.length; row++) {
            if (delta > 0) {
                tSpelling.rows[row].deleteCell(2);
            } else {
                tSpelling.rows[row].insertCell(2);
            }
        }
    }
    for (i = 0; i < comps.length; i++){
        var col = i + 2;
        var i1 = (shiftPosition >= 0 && i < size1) || (i >= -shiftPosition && i < size1 - shiftPosition) ? (shiftPosition >= 0 ? i : i + shiftPosition) : -1;
        var i2 = (shiftPosition <= 0 && i < size2) || (i >= shiftPosition && i < size2 + shiftPosition) ? (shiftPosition >= 0 ? i - shiftPosition : i) : -1;
        tSpelling.rows[0].cells[col].textContent = i1 >= 0 ? label(value.one.openLeft, value.one.matches.length, i1) : "";
        createMatch(tSpelling.rows[1].cells[col], 1, i1);
        createComp(tSpelling.rows[2].cells[col], i, i1 >= 0 && i2 >= 0);
        tSpelling.rows[4].cells[col].textContent = i2 >= 0 ? label(value.two.openLeft, value.two.matches.length, i2) : "";
        createMatch(tSpelling.rows[3].cells[col], 2, i2);
    }
    createComp(tSpelling.rows[2].cells[1], -1, isCompLeftPossible());
    createComp(tSpelling.rows[2].cells[2 + comps.length], -1, isCompRightPossible());
}

var value = {
    "one": {
        "openLeft": false,
        "openRight": false,
        "matches": []
    },
    "two": {
        "openLeft": false,
        "openRight": false,
        "matches": []
    },
    "shiftPosition": 0,
    "comps": [],
    "compOpen": 0
};

var language = document.getElementsByTagName('html')[0].getAttribute('lang');

function spellingInit(){
    "use strict";
    var cc = [jQuery('#cc1'), jQuery('#cc2')], i;
    var popup = jQuery('#popup'), explanation = jQuery('#explanation');
    jQuery.each(cc, function(index, value){
        for (i = 0; i < 14; i++) {
            value.append(new Option(i, i));
        }
    });
    cc[0].change(function(event){
        sizeChanged(1, event.target.selectedIndex);
    });
    cc[1].change(function(event){
        sizeChanged(2, event.target.selectedIndex);
    });
    jQuery('#l1').click(function(event){
        openChanged(1, 'l', event.target.checked);
    }) ;
    jQuery('#r1').click(function(event){
        openChanged(1, 'r', event.target.checked);
    }) ;
    jQuery('#l2').click(function(event){
        openChanged(2, 'l', event.target.checked);
    }) ;
    jQuery('#r2').click(function(event){
        openChanged(2, 'r', event.target.checked);
    }) ;
    jQuery('#shl1, #shr2').click(function(event){
        shiftChanged('l');
    });
    jQuery('#shr1, #shl2').click(function(event){
        shiftChanged('r');
    });
    var ulMatchList = jQuery("#matchList"), j;
    var matchClick = function(index){
        return function(){
            var data = popup.data("match");
            var matches = data.matchIndex === 1 ? value.one.matches : value.two.matches;
            matches[data.index][0] = index;
            createMatch(data.parent, data.matchIndex, data.index);
            updateDisplay();
        };
    };
    var matchMouseEnter = function(index){
        return function(){
            var list = EXPLANATION[language] || EXPLANATION[''];
            explanation.text(list[index]);
        };
    };
    var matchMouseLeave = function(){
        return function(){
            explanation.text("Help...");
        };
    };
    for (j = 0; j < MATCH.length; j++){
        var liElement = document.createElement("li"), tn = document.createTextNode(MATCH[j]);
        liElement.appendChild(tn);
        liElement.onclick = matchClick(j);
        liElement.onmouseenter = matchMouseEnter(j);
        liElement.onmouseleave = matchMouseLeave();
        ulMatchList.append(liElement);
    }
    jQuery('#match1').click(function(event){
        var data = popup.data("match");
        createMatch(data.parent, data.matchIndex, 1);
    });
    value = eval("(" + jQuery("#advancedSpelling").val() + ")");
    adjust();
    updateDisplay();
}

jQuery(document).click(function(event){
    "use strict";
    var popup = jQuery("#popup");
    if (popup.is(":visible")){
        popup.hide();
    }
});

jQuery(document).ready(function() {
    console.log( "ready!" );
    spellingInit();
});


