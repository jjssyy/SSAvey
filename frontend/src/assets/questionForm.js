// function singleMake(data) {
//   return `<div id="SINGLE" class="dragthing-drag">
//   <p class="q-type">객관식(단일선택)</p>
//   <div class="question">
//     <textarea
//       class="question-title"
//       type="text"
//       placeholder="질문제목을 입력해주세요."
//       value="${data['q_explantion']}"
//     />
//     <button class="question-garbage">
//       <i class="fas fa-trash-alt"></i>
//     </button>
//     <div class="q-option">
//       <div class="question-control">
//         <p class="q-option-item-c1">'선택'</p>
//         <p>또는</p>
//         <p class="q-option-item-c2">'기타' 추가</p>
//       </div>
//     </div>
//     <div class="is-required">
//       <p class="is-required-text">필수 응답</p>
//       <label class="toggle-control">
//         <input type="checkbox" checked="checked" />
//         <span class="control"></span>
//       </label>
//     </div>
//   </div>
//   </div>`
// }
// function singleOptionMake(data) {
//   return `<div class="q-option-item">
//   <div class="q-option-item-center">
//     <input type="radio" name="SINGLE" value="1" />
//   </div>
//   <input type="text" placeholder="선택" />
//   <i class="fas fa-times" style="display: none;"></i>
// </div>`
// }
// function singleOptionShortMake(data) {
//   return `<div class="q-option-item">
//   <div class="q-option-item-center">
//     <input type="radio" name="SINGLE" value="1" />
//   </div>
//   <input type="text" placeholder="기타" disabled="true" />
//   <i class="fas fa-times" style="display: none;"></i>
// </div>`
// }

// function multipleMake(data) {
//   return `<div id="MULTIPLE" class="dragthing-drag">
//   <p class="q-type">객관식(복수선택)</p>
//   <div class="question">
//     <textarea
//       class="question-title"
//       type="text"
//       placeholder="질문제목을 입력해주세요."
//     />
//     <button class="question-garbage">
//       <i class="fas fa-trash-alt"></i>
//     </button>
//     <div class="q-option">
//       <div class="q-option-item">
//         <div class="q-option-item-center">
//           <input type="checkbox" name="MULTIPLE" value="1" />
//         </div>
//         <input type="text" placeholder="선택" />
//         <i class="fas fa-times" style="display: none;"></i>
//       </div>
//       <div class="q-option-item">
//         <div class="q-option-item-center">
//           <input type="checkbox" name="MULTIPLE" value="2" />
//         </div>
//         <input type="text" placeholder="선택" />
//         <i class="fas fa-times"></i>
//       </div>
//       <div class="q-option-item">
//         <div class="q-option-item-center">
//           <input type="checkbox" name="MULTIPLE" value="3" />
//         </div>
//         <input type="text" placeholder="선택" />
//         <i class="fas fa-times"></i>
//       </div>
//       <div class="question-control">
//         <p class="q-option-item-c1">'선택'</p>
//         <p>또는</p>
//         <p class="q-option-item-c2">'기타' 추가</p>
//       </div>
//     </div>
//     <div class="is-required">
//       <p class="is-required-text">필수 응답</p>
//       <label class="toggle-control">
//         <input type="checkbox" checked="checked" />
//         <span class="control"></span>
//       </label>
//     </div>
//   </div>
//   </div>`
// }
// function shortMake(data) {
//   return `<div id="SHORT" class="dragthing-drag">
//   <p class="q-type">주관식</p>
//   <div class="question">
//     <textarea
//       class="question-title"
//       type="text"
//       placeholder="질문제목을 입력해주세요."
//     />
//     <button class="question-garbage">
//       <i class="fas fa-trash-alt"></i>
//     </button>
//     <div class="q-option">
//       <textarea
//         class="item-short"
//         type="text"
//         placeholder="주관식 텍스트"
//       />
//     </div>
//     <div class="is-required">
//       <p class="is-required-text">필수 응답</p>
//       <label class="toggle-control">
//         <input type="checkbox" checked="checked" />
//         <span class="control"></span>
//       </label>
//     </div>
//   </div>
//   </div>`
// }

// export { singleMake, multipleMake, shortMake }
