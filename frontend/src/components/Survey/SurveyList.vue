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
              <input type="checkbox" checked="checked" @click="isAnony" />
              <span class="control"></span>
            </label>
          </div>
        </div>
        <div id="drop-parent" class="drop-parent"></div>
        <div class="section-control">
          <div class="control-box">
            <button class="create" @click="createSurvey">
              만들기
            </button>
          </div>
        </div>
      </div>
    </section>
    <section class="drag-section">
      <div class="section-containers">
        <div class="sub-button">
          <button
            class="sub-button-q"
            :class="{ 'btn-active': isClkFormMenu }"
            @click="clkFormMenu"
          >
            양식
          </button>
          <button
            class="sub-button-t"
            :class="{ 'btn-active': isClkTemplateMenu }"
            @click="clkTemplateMenu"
          >
            탬플릿
          </button>
        </div>
        <div v-show="isClkFormMenu">
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
                    <p class="q-option-item-c1">'객관식'</p>
                    <p>또는</p>
                    <p class="q-option-item-c2">'주관식' 추가</p>
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
                    <p class="q-option-item-c1">'객관식'</p>
                    <p>또는</p>
                    <p class="q-option-item-c2">'주관식' 추가</p>
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
        <div v-show="isClkTemplateMenu"><p>g2</p></div>
      </div>
    </section>
  </div>
</template>

<script>
import TemplateApi from '@/api/TemplateApi'
import dragula from 'dragula'
import autoScroll from 'dom-autoscroller'
// import { singleEl, multipleEl, shortEl } from '@/assets/questionForm'
export default {
  data() {
    return {
      isClkFormMenu: true,
      isClkTemplateMenu: false,
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
      //   q_explanation: null,
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
            q_explanation: null,
            q_type: null, // SINGLE, MULTIPLE, SHORT
            is_required: true,
            q_option: [], // q_type이 SHORT 경우 q_option은 빈 배열
          }
          question.q_number = `${index1 + 1}`
          question.q_explanation = `${element1.childNodes[1].childNodes[0].value}`
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
      if (this.surveyOrTemplate == 'survey') {
        console.log(this.survey)
        let payload = {
          isWriting: true,
          isClkUpdate: false,
          survey: this.survey,
        }
        this.$store.commit('resetSurveyDragThing', [])
        document.querySelector('.drop-parent').childNodes.forEach(element => {
          this.$store.commit('setSurveyDragThing', element.outerHTML)
        })
        this.$store.commit('setSurveySet', payload)
        this.$router.push('/surveyset')
      } else if (this.surveyOrTemplate == 'template') {
        let tempSurvey = this.survey
        tempSurvey['t_title'] = tempSurvey.title
        tempSurvey['t_explain'] = tempSurvey.explain
        delete tempSurvey.title
        delete tempSurvey.explain
        delete tempSurvey.target
        delete tempSurvey.incomplete
        delete tempSurvey.share
        delete tempSurvey.start_date
        delete tempSurvey.end_date
        delete tempSurvey.state
        delete tempSurvey.use_template
        delete tempSurvey.template
        delete tempSurvey.complete
        console.log(tempSurvey)
        TemplateApi.makeTemplate(
          tempSurvey,
          res => {
            console.log(res)
            let payload = {
              isWriting: false,
              isClkUpdate: false,
              survey: {},
            }
            this.$store.commit('setSurveySet', payload)
          },
          err => {
            console.log(err)
          },
        )
      }
    },
    clkFormMenu() {
      this.isClkFormMenu = true
      this.isClkTemplateMenu = false
    },
    clkTemplateMenu() {
      this.isClkFormMenu = false
      this.isClkTemplateMenu = true
    },
  },
  mounted() {
    if (
      this.$store.getters.getSurveySet.isWriting == true &&
      this.$store.state.surveySet.isClkUpdate == false
    ) {
      this.$swal({
        title: '작성중인 설문지가 존재합니다.',
        text: '이어서 작성하시겠습니까?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '계속',
        cancelButtonText: '취소',
      }).then(result => {
        if (result.isConfirmed) {
          this.$router.push('/surveyset')
        } else {
          let payload = {
            survey: {},
            isClkUpdate: false,
            isWriting: false,
          }
          this.$store.commit('resetSurveyDragThing', [])
          this.$store.commit('setSurveySet', payload)
        }
      })
    }
    // 수정클릭시 문항 뿌려주기
    if (this.$store.state.surveySet.isClkUpdate) {
      let dragthings = this.$store.getters.getSurveyDragThing
      dragthings.forEach(function(element) {
        document
          .querySelector('.drop-parent')
          .insertAdjacentHTML('beforeend', element)
      })
      // 제목, 설명
      this.survey.title = this.$store.state.surveySet.survey.title
      this.survey.explain = this.$store.state.surveySet.survey.explain
      // 설문 클릭
      document.querySelector('.option-s-center>input').click()
      // 실명설문인지 아닌지
      if (this.$store.state.surveySet.survey.is_anony) {
        document.querySelector('.option-anony>label>input').click()
      }
      let allDropEl = document.querySelectorAll('.dragthing-drop')
      this.$store.state.surveySet.survey.question.forEach(function(
        element,
        index,
      ) {
        // 질문제목 넣어줘
        allDropEl[index].childNodes[1].childNodes[0].value =
          element.q_explanation
        // 필수 응답인지 아닌지 체크해줘
        if (!element.is_required) {
          allDropEl[
            index
          ].childNodes[1].childNodes[4].childNodes[1].childNodes[0].click()
        }
        // garbage-btn 활성화
        allDropEl[
          index
        ].childNodes[1].childNodes[2].childNodes[0].addEventListener(
          'click',
          function() {
            allDropEl[index].remove()
          },
        )
        // 객관식 또는 주관식 추가하는거 버튼 활성화시켜줘
        if (element.q_type != 'SHORT') {
          let question = allDropEl[index].childNodes[1].childNodes[3]
          let temp = question.childNodes
          // 기존 태그 삭제 활성화, 기존 input값 넣기
          temp.forEach(function(element2, index) {
            if (index != temp.length - 1) {
              element2.childNodes[1].value =
                element.q_option[index]['o_explanation']
              element2.childNodes[2].addEventListener('click', function() {
                element2.remove()
              })
            }
          })
          // 객관식 버튼 활성화
          temp[temp.length - 1].childNodes[0].addEventListener(
            'click',
            function() {
              let cloneOption = temp[0].cloneNode(true)
              cloneOption.childNodes[1].value = ''
              cloneOption.childNodes[2].style.display = 'block'
              cloneOption.childNodes[2].addEventListener('click', function() {
                cloneOption.remove()
              })
              question.insertBefore(cloneOption, temp[temp.length - 1])
            },
          )
          // 주관식 버튼 활성화
          temp[temp.length - 1].childNodes[2].addEventListener(
            'click',
            function() {
              let cloneOption = temp[0].cloneNode(true)
              cloneOption.childNodes[1].value = ''
              cloneOption.childNodes[2].style.display = 'block'
              cloneOption.childNodes[2].addEventListener('click', function() {
                cloneOption.remove()
              })
              cloneOption.childNodes[1].setAttribute('placeholder', '기타')
              cloneOption.childNodes[1].setAttribute('disabled', 'true')
              question.insertBefore(cloneOption, temp[temp.length - 1])
            },
          )
        }
      })
    }
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
  destroyed() {
    this.$store.commit('setIsClkUpdate', false)
  },
}
</script>

<style scroped src="./../../css/survey/survey/survey-list.css"></style>
