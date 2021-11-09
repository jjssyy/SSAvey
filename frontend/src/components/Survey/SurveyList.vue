<template>
  <div class="dragula-container">
    <section class="drop-section">
      <div class="section-containers">
        <div class="section-title">
          <div class="survey-title">
            <textarea
              type="text"
              placeholder="설문지 제목"
              v-model="survey.title"
            ></textarea>
          </div>
          <div class="survey-explain">
            <textarea
              type="text"
              placeholder="설문지 설명"
              v-model="survey.explain"
            ></textarea>
          </div>
        </div>
        <div class="section-survey-option">
          <div class="option-st">
            <div class="option-s">
              <div class="option-s-center">
                <input
                  type="radio"
                  name="option-st"
                  value="survey"
                  v-model="surveyOrTemplate"
                />
              </div>
              <p>설문지</p>
            </div>
            <div class="option-t">
              <div class="option-t-center">
                <input
                  type="radio"
                  name="option-st"
                  value="template"
                  v-model="surveyOrTemplate"
                />
              </div>
              <p>탬플릿</p>
            </div>
          </div>
          <div class="option-anony">
            <p class="option-anony-item">실명설문</p>
            <label class="toggle-control">
              <input type="checkbox" checked="checked" />
              <span class="control"></span>
            </label>
          </div>
        </div>
        <div id="drop-parent" class="drop-parent"></div>
        <div class="section-control">
          <div class="control-box">
            <button class="cancel">취소</button>
            <button class="create" @click="createSurvey">만들기</button>
          </div>
        </div>
      </div>
    </section>
    <section class="drag-section">
      <div class="section-containers">
        <div class="sub-button">
          <button class="sub-button-q">양식</button>
          <button class="sub-button-t">탬플릿</button>
        </div>
        <div class="sub-title">
          <p class="sub-title-text">드래그해서</p>
          <p class="sub-title-text">설문지를 생성하세요.</p>
        </div>
        <div id="drag-parent" class="drag-parent">
          <div id="SINGLE" class="dragthing-drag">
            <p class="q-type">객관식(단일선택)</p>
            <div class="question">
              <textarea
                class="question-title"
                type="text"
                placeholder="질문제목을 입력해주세요."
              />
              <button class="question-garbage">
                <i class="fas fa-trash-alt"></i>
              </button>
              <div class="q-option">
                <div class="q-option-item">
                  <div class="q-option-item-center">
                    <input type="radio" name="SINGLE" value="1" />
                  </div>
                  <input type="text" placeholder="선택" />
                  <i class="fas fa-times" style="display: none;"></i>
                </div>
                <div class="q-option-item">
                  <div class="q-option-item-center">
                    <input type="radio" name="SINGLE" value="2" />
                  </div>
                  <input type="text" placeholder="선택" />
                  <i class="fas fa-times"></i>
                </div>
                <div class="q-option-item">
                  <div class="q-option-item-center">
                    <input type="radio" name="SINGLE" value="3" />
                  </div>
                  <input type="text" placeholder="선택" />
                  <i class="fas fa-times"></i>
                </div>
                <div class="question-control">
                  <p class="q-option-item-c1">'선택'</p>
                  <p>또는</p>
                  <p class="q-option-item-c2">'기타' 추가</p>
                </div>
              </div>
              <div class="is-required">
                <p class="is-required-text">필수 응답</p>
                <label class="toggle-control">
                  <input type="checkbox" checked="checked" />
                  <span class="control"></span>
                </label>
              </div>
              <!-- 추가기능 -->
              <!-- <div class="question-detail-btn">
                <p>세부문항 생성</p>
                <button><i class="fas fa-angle-double-down"></i></button>
              </div> -->
            </div>
          </div>
          <div id="MULTIPLE" class="dragthing-drag">
            <p class="q-type">객관식(복수선택)</p>
            <div class="question">
              <textarea
                class="question-title"
                type="text"
                placeholder="질문제목을 입력해주세요."
              />
              <button class="question-garbage">
                <i class="fas fa-trash-alt"></i>
              </button>
              <div class="q-option">
                <div class="q-option-item">
                  <div class="q-option-item-center">
                    <input type="checkbox" name="MULTIPLE" value="1" />
                  </div>
                  <input type="text" placeholder="선택" />
                  <i class="fas fa-times" style="display: none;"></i>
                </div>
                <div class="q-option-item">
                  <div class="q-option-item-center">
                    <input type="checkbox" name="MULTIPLE" value="2" />
                  </div>
                  <input type="text" placeholder="선택" />
                  <i class="fas fa-times"></i>
                </div>
                <div class="q-option-item">
                  <div class="q-option-item-center">
                    <input type="checkbox" name="MULTIPLE" value="3" />
                  </div>
                  <input type="text" placeholder="선택" />
                  <i class="fas fa-times"></i>
                </div>
                <div class="question-control">
                  <p class="q-option-item-c1">'선택'</p>
                  <p>또는</p>
                  <p class="q-option-item-c2">'기타' 추가</p>
                </div>
              </div>
              <div class="is-required">
                <p class="is-required-text">필수 응답</p>
                <label class="toggle-control">
                  <input type="checkbox" checked="checked" />
                  <span class="control"></span>
                </label>
              </div>
            </div>
          </div>
          <div id="SHORT" class="dragthing-drag">
            <p class="q-type">주관식</p>
            <div class="question">
              <textarea
                class="question-title"
                type="text"
                placeholder="질문제목을 입력해주세요."
              />
              <button class="question-garbage">
                <i class="fas fa-trash-alt"></i>
              </button>
              <div class="q-option">
                <textarea
                  class="item-short"
                  type="text"
                  placeholder="주관식 텍스트"
                />
              </div>
              <div class="is-required">
                <p class="is-required-text">필수 응답</p>
                <label class="toggle-control">
                  <input type="checkbox" checked="checked" />
                  <span class="control"></span>
                </label>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import dragula from 'dragula'
import autoScroll from 'dom-autoscroller'
export default {
  data() {
    return {
      surveyOrTemplate: null,
      survey: {
        title: null,
        explain: null,
        is_anony: false, // 익명설문인지
        start_date: null, // surveyset 페이지에서 작성
        end_date: null, // surveyset 페이지에서 작성
        question: [],
        state: 'EXPECTED', // surveyset 페이지에서 보낼 때 결정
        share: [], // surveyset에서
        target: [], // surveyset에서
        complete: [], //빈 배열로
        incomplete: [], // surveyset에서 target과 동일한 값으로
        use_template: null,
        template: null,
        writer: this.$store.state.uid,
      },
      // question: {
      //   q_number: null, // 1, 1-1, 1-2, 2
      //   q_explantion: null,
      //   q_type: null, // SINGLE, MULTIPLE, SHORT
      //   is_required: true,
      //   q_option: [], // q_type이 SHORT 경우 q_option은 빈 배열
      // },
      // q_option: {
      //   o_number: null, // 문항의의 선택지 번호
      //   o_explanation: null,
      //   is_short: null, // 객관식의 o_number 선택지가 '기타'냐?
      // },
    }
  },
  methods: {
    isAnony() {
      this.survey.is_anony = !this.survey.is_anony
    },
    createSurvey() {
      // 사용자에게 설문작성에 무엇이 잘못되었는지 알려주기.
      // if (!this.survey.title) {
      //   console.log('설문지 제목을 입력해 주세요.')
      //   if (!this.survey.explain) {
      //     console.log('설문지 설명을 입력해 주세요.')
      //     if (document.querySelector('.drop-parent').childNodes.length == 0) {
      //       console.log('설문 문항을 작성해 주세요.')
      //     }
      //   }
      // }
      document
        .querySelector('.drop-parent')
        .childNodes.forEach((element1, index1) => {
          let question = {
            q_number: null, // 1, 1-1, 1-2, 2
            q_explantion: null,
            q_type: null, // SINGLE, MULTIPLE, SHORT
            is_required: true,
            q_option: [], // q_type이 SHORT 경우 q_option은 빈 배열
          }
          question.q_number = `${index1 + 1}`
          question.q_explantion = `${element1.childNodes[1].childNodes[0].value}`
          question.q_type = `${element1.id}`
          if (
            element1.childNodes[1].childNodes[4].childNodes[1].childNodes[0].classList.contains(
              'not-required',
            )
          ) {
            question.is_required = false
          } else {
            question.is_required = true
          }
          if (question.q_type != 'SHORT') {
            element1.childNodes[1].childNodes[3].childNodes.forEach(
              (element2, index2) => {
                let q_option = {
                  o_number: null, // 문항의의 선택지 번호
                  o_explanation: null,
                  is_short: null, // 객관식의 o_number 선택지가 '기타'냐?
                }
                if (element2.classList.contains('q-option-item')) {
                  if (element2.childNodes[1].placeholder == '선택') {
                    q_option.o_number = `${index2 + 1}`
                    q_option.o_explanation = `${element2.childNodes[1].value}`
                    q_option.is_short = false
                  } else {
                    q_option.o_number = `${index2 + 1}`
                    q_option.o_explanation = `${element2.childNodes[1].placeholder}`
                    q_option.is_short = true
                  }
                  question.q_option.push(q_option)
                }
              },
            )
          }
          this.survey.question.push(question)
        })
      // console.log(this.survey)
      let payload = {
        isWriting: true,
        survey: this.survey,
      }
      this.$store.commit('setSurveySet', payload)
      // console.log(this.$store.getters.getSurveySet)
      this.$router.push('/surveyset')
    },
  },
  mounted() {
    // toggle
    document.querySelectorAll('.toggle-control').forEach(function(element) {
      element.addEventListener('click', function(e) {
        if (e.target.classList.contains('not-required')) {
          e.target.classList.remove('not-required')
        } else {
          e.target.classList.add('not-required')
        }
      })
    })
    // dragula contoller
    var drake = dragula(
      [
        document.querySelector('#drag-parent'),
        document.querySelector('#drop-parent'),
      ],
      {
        accepts: function(el, target) {
          if (target.id == 'drag-parent') {
            return false
          } else {
            return true
          }
        },
        revertOnSpill: true,
      },
    )
    drake.on('drop', function(el, target, source) {
      if (target != source) {
        let cloneEl = el.cloneNode(true)
        cloneEl.classList.remove('gu-transit')
        if (el.id == 'SINGLE') {
          document
            .querySelector('#drag-parent')
            .insertBefore(
              cloneEl,
              document.querySelector('#drag-parent>#MULTIPLE'),
            )
        } else if (el.id == 'MULTIPLE') {
          document
            .querySelector('#drag-parent')
            .insertBefore(
              cloneEl,
              document.querySelector('#drag-parent>#SHORT'),
            )
        } else if (el.id == 'SHORT') {
          document.querySelector('#drag-parent').appendChild(cloneEl)
        }
        // garbage-click
        document
          .querySelector(`.dragthing-drag>.question>.question-garbage`)
          .addEventListener('click', function(e) {
            let searchParent = e.target.parentElement
            let check = true
            while (check == true) {
              if (searchParent.classList) {
                if (searchParent.classList.contains('dragthing-drop')) {
                  searchParent.remove()
                  break
                } else {
                  searchParent = searchParent.parentElement
                }
              } else {
                searchParent = searchParent.parentElement
              }
            }
          })
        // 선택or기타 버튼 탐색
        if (el.id != 'SHORT') {
          let controlElement = null
          let qOption = el.childNodes[1].childNodes[3]
          qOption.childNodes.forEach(function(element) {
            if (element.classList.contains('question-control')) {
              controlElement = element
              return false
            }
          })
          // 초기 선택 두개 삭제버튼 활성화
          qOption.childNodes.forEach(function(element) {
            if (element.classList.contains('q-option-item')) {
              element.childNodes[2].addEventListener('click', function() {
                element.remove()
              })
            }
          })
          // 선택추가
          controlElement.childNodes[0].addEventListener('click', function() {
            let cloneqOptionItem = qOption.childNodes[0].cloneNode(true)
            cloneqOptionItem.childNodes[1].value = ''
            cloneqOptionItem.childNodes[2].style.display = 'block'
            cloneqOptionItem.childNodes[2].addEventListener(
              'click',
              function() {
                cloneqOptionItem.remove()
              },
            )
            qOption.insertBefore(cloneqOptionItem, controlElement)
          })
          // 기타추가
          controlElement.childNodes[2].addEventListener('click', function() {
            let cloneqOptionItem = qOption.childNodes[0].cloneNode(true)
            cloneqOptionItem.childNodes[1].value = ''
            cloneqOptionItem.childNodes[2].style.display = 'block'
            cloneqOptionItem.childNodes[2].addEventListener(
              'click',
              function() {
                cloneqOptionItem.remove()
              },
            )
            // input
            cloneqOptionItem.childNodes[1].setAttribute('placeholder', '기타')
            cloneqOptionItem.childNodes[1].setAttribute('disabled', 'true')
            qOption.insertBefore(cloneqOptionItem, controlElement)
          })
        }
        el.classList.remove('dragthing-drag')
        el.classList.add('dragthing-drop')
        // 여기에 번호 생각해줘야지..
      }
    })
    autoScroll([window, document.querySelector('#drop-parent')], {
      margin: 20,
      maxSpeed: 15,
      autoScroll: function() {
        return this.down && drake.dragging
      },
    })
  },
}
</script>

<style scroped src="./../../css/survey/survey/survey-list.css"></style>
