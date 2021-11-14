function singleMake(question) {
  console.log('이게 들어옴', question)
  let result = ''
  result +=
    `<div id="SINGLE" class="dragthing-drop"><p class="q-type">객관식(단일선택)</p><div class="question"><textarea class="question-title" type="text"placeholder="질문제목을 입력해주세요."` +
    ` >${question['q_explanation']}</textarea>`
  result += `<button class="question-garbage"><i class="fas fa-trash-alt"></i></button><div class="q-option">`
  question.q_option.forEach(function(element, index) {
    console.log(index, element)
    if (index == 0) {
      result += `<div class="q-option-item"><div class="q-option-item-center"><input type="radio" name="SINGLE" value="${index +
        1}" /></div><input type="text" placeholder="선택" value="${
        element['o_explanation']
      }"/><i class="fas fa-times" style="display: none;"></i></div>`
    } else {
      if (element['short_answer']) {
        result += `<div class="q-option-item"><div class="q-option-item-center"><input type="radio" name="SINGLE" value="${index +
          1}" /></div><input type="text" placeholder="기타" disabled="true"/><i class="fas fa-times"></i></div>`
      } else {
        result += `<div class="q-option-item"><div class="q-option-item-center"><input type="radio" name="SINGLE" value="${index +
          1}" /></div><input type="text" placeholder="선택" value="${
          element['o_explanation']
        }"/><i class="fas fa-times"></i></div>`
      }
    }
  })
  result += `<div class="question-control"><p class="q-option-item-c1">'선택'</p><p>또는</p><p class="q-option-item-c2">'기타' 추가</p></div></div><div class="is-required"><p class="is-required-text">필수 응답</p><label class="toggle-control"><input type="checkbox" checked="checked" /><span class="control"></span></label></div>`
  result += `</div></div>`
  return result
}
function multipleMake(question) {
  let result = ''
  result +=
    `<div id="MULTIPLE" class="dragthing-drop"><p class="q-type">객관식(복수선택)</p><div class="question"><textarea class="question-title" type="text"placeholder="질문제목을 입력해주세요."` +
    ` >${question['q_explanation']}</textarea>`
  result += `<button class="question-garbage"><i class="fas fa-trash-alt"></i></button><div class="q-option">`
  question.q_option.forEach(function(element, index) {
    if (index == 0) {
      result += `<div class="q-option-item"><div class="q-option-item-center"><input type="checkbox" name="MULTIPLE" value="${index +
        1}" /></div><input type="text" placeholder="선택" value="${
        element['o_explanation']
      }"/><i class="fas fa-times" style="display: none;"></i></div>`
    } else {
      if (element['short_answer']) {
        result += `<div class="q-option-item"><div class="q-option-item-center"><input type="checkbox" name="MULTIPLE" value="${index +
          1}" /></div><input type="text" placeholder="기타" disabled="true"/><i class="fas fa-times"></i></div>`
      } else {
        result += `<div class="q-option-item"><div class="q-option-item-center"><input type="checkbox" name="MULTIPLE" value="${index +
          1}" /></div><input type="text" placeholder="선택" value="${
          element['o_explanation']
        }"/><i class="fas fa-times"></i></div>`
      }
    }
  })
  result += `<div class="question-control"><p class="q-option-item-c1">'선택'</p><p>또는</p><p class="q-option-item-c2">'기타' 추가</p></div></div><div class="is-required"><p class="is-required-text">필수 응답</p><label class="toggle-control"><input type="checkbox" checked="checked" /><span class="control"></span></label></div>`
  result += `</div></div>`
  return result
}
function shortMake(question) {
  let result = ''
  result += `<div id="SHORT" class="dragthing-drop"><p class="q-type">주관식</p><div class="question"><textarea class="question-title" type="text" placeholder="질문제목을 입력해주세요.">${question['q_explanation']}</textarea>`
  result += `<button class="question-garbage"><i class="fas fa-trash-alt"></i></button><div class="q-option"><textarea class="item-short" type="text" placeholder="주관식 텍스트"></textarea></div>`
  result += `<div class="is-required"><p class="is-required-text">필수 응답</p><label class="toggle-control"><input type="checkbox" checked="checked" /><span class="control"></span></label></div></div></div>`
  return result
}

export { singleMake, multipleMake, shortMake }
